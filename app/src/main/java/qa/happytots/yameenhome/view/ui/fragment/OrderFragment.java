package qa.happytots.yameenhome.view.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.YameenApplication;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.delivery.DeliverySlot;
import qa.happytots.yameenhome.model.fetchaddress.Address;
import qa.happytots.yameenhome.model.order.OrderSummary;
import qa.happytots.yameenhome.model.order_overview.OrderTotal;
import qa.happytots.yameenhome.model.order_overview.ProductDetailHeader;
import qa.happytots.yameenhome.model.order_overview.ShipmentAddress;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseData;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseTotal;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.Cart_Response;
import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.view.adapter.OrderOverviewAdapter;
import qa.happytots.yameenhome.view.ui.activities.CheckoutActivity;
import qa.happytots.yameenhome.view.ui.activities.SuccessTransationActivity;

public class OrderFragment extends Fragment implements NetworkManager.OnNetworkResponseListener {

    private YameenTextView tvPlaceOrder;

    private List<Bridge> mOrderOverViewDetails;
    private OrderOverviewAdapter mAdapter;

    private boolean isCartAPICalled = false;

    private OrderTotal total = new OrderTotal();
    private ShipmentAddress address = new ShipmentAddress();

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderOverViewDetails = new ArrayList<>();
        mAdapter = new OrderOverviewAdapter(mOrderOverViewDetails);
    }

    public static OrderFragment getInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvPlaceOrder = view.findViewById(R.id.tv_place_order);
        RecyclerView rvOrder = view.findViewById(R.id.rv_order);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvOrder.setLayoutManager(manager);
        rvOrder.setAdapter(mAdapter);

        tvPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = ((CheckoutActivity) getActivity()).getPaymentMethodCode();
                if (code.equalsIgnoreCase("cod")) {
                    goToSuccessPage();
                } else {
                    ((CheckoutActivity) getActivity()).sendMessage(null);
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            callCartApi();
        }
    }
    private void callCartApi() {
        isCartAPICalled = true;
        UtilityClass.showProgressDialog(getContext());
        DatabaseHandler handler = new DatabaseHandler(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForFetchingCarts(handler.getSessionValue());
    }



    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        if (response.body instanceof Cart_Response) {
            isCartAPICalled = false;
            Cart_Response cartResponse = (Cart_Response) response.body;
            CartResponseData responseData = cartResponse.getData();

            OrderSummary summary = new OrderSummary();
            DeliverySlot slot = ((CheckoutActivity) getActivity()).getSelectedDeliverySlot();
            summary.setDate(YameenApplication.getSlot(slot.getSelectedDate()));
            if (slot.getSelectedSlot() == 1) {
                summary.setSlot(R.string.label_slot_morning);
            } else {
                summary.setSlot(R.string.label_slot_evening);
            }
            mOrderOverViewDetails.add(summary);

            Double totalRaw = responseData.getTotalRaw();
            if (totalRaw < 40) {
                total.setDeliveryFree(false);
            } else {
                total.setDeliveryFree(true);
            }
            if (((CheckoutActivity) getActivity()).isCOD()) {
                total.setCOD(true);
            } else {
                total.setCOD(false);
            }

            ProductDetailHeader header = new ProductDetailHeader();
            mOrderOverViewDetails.add(header);
            mOrderOverViewDetails.addAll(responseData.getProducts());

            setTotalOverView(responseData.getTotals());
            mOrderOverViewDetails.add(total);

            Address selectedAddress = ((CheckoutActivity) getActivity()).getSelectedAddress();
            address.setName(selectedAddress.getFirstname() + " " + selectedAddress.getLastname());
            address.setAddress1(selectedAddress.getAddress1());
            address.setAddress2(selectedAddress.getAddress2());
            mOrderOverViewDetails.add(address);

            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        qa.happytots.yameenhome.components.Toast.makeToast("Something went wrong, Please try again");
    }

    @Override
    public void noInternet() {
        UtilityClass.dismissProgressDialog();
        qa.happytots.yameenhome.components.Toast.makeToast(R.string.no_internet);
    }

    private void setTotalOverView(List<CartResponseTotal> cartResponseTotals) {
        for (CartResponseTotal cTotal : cartResponseTotals) {
            if (cTotal.getTitle().equals("Sub-Total")) {
                total.setSubTotal(cTotal);
            } else if (cTotal.getTitle().equals("Total")) {
                total.setGrandTotal(cTotal);
                ((CheckoutActivity) getActivity()).setAmount(cTotal.getValue());
            } else if (cTotal.getTitle().contains("Coupon")) {
                total.setCoupon(cTotal);
            } else if (cTotal.getTitle().equalsIgnoreCase("Flat Shipping Rate")) {
                total.setShipmentTotal(cTotal);
            } else {
                total.setShipmentTotal(cTotal);
            }
        }
    }



    private void goToSuccessPage() {
       // qa.happytots.yameenhome.components.Toast.makeToast("Placed the order");
        Intent intent = new Intent(getContext(), SuccessTransationActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
