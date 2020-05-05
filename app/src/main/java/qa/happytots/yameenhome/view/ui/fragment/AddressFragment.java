package qa.happytots.yameenhome.view.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.CommonResponse;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.delivery.DeliverySlot;
import qa.happytots.yameenhome.model.fetchaddress.Address;
import qa.happytots.yameenhome.model.fetchaddress.DeliveryAddressResponse;
import qa.happytots.yameenhome.model.payment.methods.request.PaymentExistingAddressRequest;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseData;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseTotal;
import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.view.adapter.DeliveryAddressAdapter;
import qa.happytots.yameenhome.view.ui.activities.AddDeliveryAddressActivity;
import qa.happytots.yameenhome.view.ui.activities.CheckoutActivity;


public class AddressFragment extends android.support.v4.app.Fragment implements DeliveryAddressAdapter.OnAddressInteractionListener,
        NetworkManager.OnNetworkResponseListener {

    private static final int REQUEST_CODE_FOR_ADDRESS_ADD_OR_EDI = 1000;
    public static final String ACTION_SUCCESS = "action_success";

    private static final int METHOD_PAYMENT_ADDRESS = 0;
    private static final int METHOD_POST_PAYMENT_ADDRESS = 1;
    private static final int METHOD_SHIPPING_ADDRESS = 2;
    private static final int METHOD_POST_SHIPPING_ADDRESS = 3;
    private static final int METHOD_PAYMENT_EXISTING_ADDRESS = 4;

    private static int mCurrentMethod = 0;

    private TextView tvAddDeliveryAddress;
    private TextView tvContinue;
    private TextView tvTotalAmount;
    private YameenTextView tvEmptyAddress;
    private RecyclerView rvDeliveryAddress;

    private DeliveryAddressAdapter mAdapter;
    private List<Bridge> mDeliveryAddresses;

    private String mAddressId;
    private boolean isAddressSelected = false;

    public AddressFragment() {
        // Required empty public constructor
    }

    public static AddressFragment getInstance() {
        return new AddressFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeliveryAddresses = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvAddDeliveryAddress = view.findViewById(R.id.tv_add_delivery_address);
        rvDeliveryAddress = view.findViewById(R.id.rv_delivery_address);
        tvContinue = view.findViewById(R.id.tv_checkout_continue);
        tvTotalAmount = view.findViewById(R.id.tv_ckeckout_total);
        tvEmptyAddress = view.findViewById(R.id.tv_address_empty);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvDeliveryAddress.setLayoutManager(manager);
        mAdapter = new DeliveryAddressAdapter(mDeliveryAddresses, this, false);
        rvDeliveryAddress.setAdapter(mAdapter);

        tvAddDeliveryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDeliveryAddress(null);
            }
        });

        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinueClick();
            }
        });

        setTotal();

        checkEmptyAddress();
        callAPIForPaymentAddress();
    }

    @Override
    public void edit(Address address) {
        goToDeliveryAddress(address);
    }

    @Override
    public void onSlotSelect(int position, int selectedDate, DeliverySlot slot) {
        slot.setSelectedSlot(selectedDate);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void addressSelection(int position, boolean isChecked, Address address) {
        updateAddress(position);
        address.setSelected(true);
        ((CheckoutActivity) getActivity()).setSelectedAddress(address);
        mAddressId = address.getAddressId();
        callAPIForExistingAddress(mAddressId);
    }

    @Override
    public void deleteAddress(int position, Address address) {

    }

    private void onContinueClick() {
        if (isAddressSelected) {
            ((CheckoutActivity) getActivity()).goToNextPage();
        } else {
            qa.happytots.yameenhome.components.Toast.makeToast("Select payment address");
        }
    }

    private void setTotal() {
        CartResponseData data = ((CheckoutActivity) getActivity()).getData();
        if (data != null) {
            String format = "Total SAR %.2f";
            Iterator<CartResponseTotal> totals = data.getTotals().iterator();
            double value = 0;
            while (totals.hasNext()) {
                CartResponseTotal total = totals.next();
                if (total.getTitle().equalsIgnoreCase("Total")) {
                    value = total.getValue();
                }
            }

            DecimalFormat twoDForm = new DecimalFormat("#.##");
            tvTotalAmount.setText(String.format(Locale.getDefault(), format, Double.valueOf(twoDForm.format(value))));
        }

    }

    private void updateAddress(int position) {
        for (int i = 0; i < mDeliveryAddresses.size(); i++) {
            Bridge bridge = mDeliveryAddresses.get(i);
            if (bridge instanceof Address) {
                Address address = (Address) bridge;
                address.setSelected(false);
            }
            if (i != position) {
                mAdapter.notifyItemChanged(i);
            }
        }
    }

    private void callAPIForPaymentAddress() {
        mCurrentMethod = METHOD_PAYMENT_ADDRESS;
        DatabaseHandler handler = new DatabaseHandler(getContext());
        UtilityClass.showProgressDialog(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(AddressFragment.this);
        manager.callAPIForFetchingPaymentAddress(handler.getSessionValue());
    }

    private void callAPIForShippingAddress() {
        mCurrentMethod = METHOD_SHIPPING_ADDRESS;
        DatabaseHandler handler = new DatabaseHandler(getContext());
        UtilityClass.showProgressDialog(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(AddressFragment.this);
        manager.callAPIForAddress(handler.getSessionValue());
    }

    private void callAPIForExistingAddress(String addressId) {
        mCurrentMethod = METHOD_PAYMENT_EXISTING_ADDRESS;
        DatabaseHandler handler = new DatabaseHandler(getContext());
        PaymentExistingAddressRequest request = new PaymentExistingAddressRequest();
        request.setAddressId(addressId);
        UtilityClass.showProgressDialog(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(AddressFragment.this);
        manager.callAPIForExistingPaymentAddress(handler.getSessionValue(), request);
    }

    private void callAPIForExistingShippingAddress(String addressId) {
        mCurrentMethod = METHOD_POST_SHIPPING_ADDRESS;
        DatabaseHandler handler = new DatabaseHandler(getContext());
        PaymentExistingAddressRequest request = new PaymentExistingAddressRequest();
        request.setAddressId(addressId);
        UtilityClass.showProgressDialog(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(AddressFragment.this);
        manager.callAPIForExistingShippingAddress(handler.getSessionValue(), request);
    }

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        if (mCurrentMethod == METHOD_PAYMENT_ADDRESS) {
            DeliveryAddressResponse addressResponse = (DeliveryAddressResponse) response.body;
            if (addressResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                mDeliveryAddresses.clear();
                mDeliveryAddresses.addAll(addressResponse.getData().getAddresses());
                if (mDeliveryAddresses.size() > 0) {
                    mAdapter.notifyDataSetChanged();
                    checkEmptyAddress();
                }
            }
//            callAPIForShippingAddress();
        } else if (mCurrentMethod == METHOD_SHIPPING_ADDRESS) {
            checkEmptyAddress();
        } else if (mCurrentMethod == METHOD_PAYMENT_EXISTING_ADDRESS) {
            CommonResponse commonResponse = (CommonResponse) response.body;
            if (commonResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                callAPIForExistingShippingAddress(mAddressId);
            }
        } else if (mCurrentMethod == METHOD_POST_SHIPPING_ADDRESS) {
            CommonResponse commonResponse = (CommonResponse) response.body;
            if (commonResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                isAddressSelected = true;
            }
        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        checkEmptyAddress();
//        Toast.makeText(getContext(), response.errorBody, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noInternet() {
        UtilityClass.dismissProgressDialog();

    }

    private void goToDeliveryAddress(Address address) {
        Intent intent = new Intent(getContext(), AddDeliveryAddressActivity.class);
        if (address != null) {
            intent.putExtra(AddDeliveryAddressActivity.BUNDLE_ADDRESS, address);
        }
        startActivityForResult(intent, REQUEST_CODE_FOR_ADDRESS_ADD_OR_EDI);
    }


    private void checkEmptyAddress() {
        if (mDeliveryAddresses.size() > 0) {
            tvEmptyAddress.setVisibility(View.GONE);
            showStatusOfBottomButtons(true);
        } else {
            tvEmptyAddress.setVisibility(View.VISIBLE);
            showStatusOfBottomButtons(false);
        }
    }

    private void showStatusOfBottomButtons(boolean status) {
        if (status) {
            tvContinue.setVisibility(View.VISIBLE);
            tvTotalAmount.setVisibility(View.VISIBLE);
        } else {
            tvContinue.setVisibility(View.GONE);
            tvTotalAmount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE_FOR_ADDRESS_ADD_OR_EDI == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                boolean actionSuccess = data.getBooleanExtra(ACTION_SUCCESS, false);
                if (actionSuccess) {
                    callAPIForPaymentAddress();
                }
            }
        }
    }
}
