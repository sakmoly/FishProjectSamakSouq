package qa.happytots.yameenhome.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.CommonResponse;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.delivery.DeliveryHeader;
import qa.happytots.yameenhome.model.delivery.DeliverySlot;
import qa.happytots.yameenhome.model.payment.methods.request.PaymentMethodRequest;
import qa.happytots.yameenhome.model.payment.methods.response.PaymentMethodResponse;
import qa.happytots.yameenhome.model.payment.methods.response.PaymentMethods;
import qa.happytots.yameenhome.model.shipping.add.ShippingMethodRequest;
import qa.happytots.yameenhome.model.shipping.fetch.response.ShippingMethodResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseData;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseTotal;
import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.view.adapter.SlotAdapter;
import qa.happytots.yameenhome.view.ui.activities.CheckoutActivity;

public class SlotFragment extends Fragment implements SlotAdapter.OnSlotInteractionListener,
        NetworkManager.OnNetworkResponseListener {

    private static final int METHOD_GET_SHIPPING = 0;
    private static final int METHOD_POST_SHIPPING = 1;
    private static final int METHOD_GET_PAYMENT = 2;
    private static final int METHOD_POST_PAYMENT = 3;
    private static final int METHOD_POST_CONFIRM_ORDER = 4;
    private static final int METHOD_PUT_CONFIRM_ORDER = 5;


    private static int mCurrentMethod = 0;


    private RecyclerView rvSlotView;
    private TextView tvTotal;
    private TextView tvContinue;

    private List<Bridge> mRvItems;
    private SlotAdapter mAdapter;

    private DatabaseHandler dbHandler;

    public SlotFragment() {
        // Required empty public constructor
    }


    public static SlotFragment getInstance() {
        return new SlotFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRvItems = new ArrayList<>();
        dbHandler = new DatabaseHandler(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSlotView = view.findViewById(R.id.rv_delivery_address);
        tvTotal = view.findViewById(R.id.tv_slot_checkout_total);
        tvContinue = view.findViewById(R.id.tv_slot_checkout_continue);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvSlotView.setLayoutManager(manager);

        populateListForSlot();
        mAdapter = new SlotAdapter(mRvItems, this);
        rvSlotView.setAdapter(mAdapter);

        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliverySlot slot = getDeliverySlotDetails();
                if (slot == null) {
                    qa.happytots.yameenhome.components.Toast.makeToast("No slots available");
                    return;
                }

                if (slot.getSelectedDate() == -1) {
                    qa.happytots.yameenhome.components.Toast.makeToast("Select a date");
                    return;
                }

                if (slot.getSelectedSlot() == 0) {
                    qa.happytots.yameenhome.components.Toast.makeToast("Select a slot");
                    return;
                }

                Bridge paymentMethod = ((CheckoutActivity) getActivity()).getSelectedPaymentMethod();
                if (paymentMethod == null) {
                    qa.happytots.yameenhome.components.Toast.makeToast("Select a payment method");
                }

                ((CheckoutActivity) getActivity()).setSelectedDeliverySlot(slot);

                ShippingMethodRequest request = new ShippingMethodRequest();

                request.setShippingMethod(((CheckoutActivity) getActivity()).isFreeDelivery() ? "free.free" : "flat.flat");
                request.setComment("string");
                callAPIForShippingMethods(request);
            }
        });

        setTotal();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            callAPIForShippingMethods();
        }
    }

    @Override
    public void onSlotSelect(int position, int selectedDate, DeliverySlot slot) {
        slot.setSelectedSlot(selectedDate);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void onPaymentSelect(int position, Bridge method) {
        ((CheckoutActivity) getActivity()).setSelectedPaymentMethod(method);
    }

    private void populateListForSlot() {
        DeliveryHeader header = new DeliveryHeader();
        header.setHeader("Delivery date and time");
        mRvItems.add(header);
        DeliverySlot slot = getSlots();
        mRvItems.add(slot);
        DeliveryHeader headerPayment = new DeliveryHeader();
        headerPayment.setHeader("Payment Options");
        mRvItems.add(headerPayment);
    }

    private DeliverySlot getSlots() {
        DeliverySlot slot = new DeliverySlot();
        slot.setSelectedSlot(0);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("EEE-dd-MMMM-yyyy", Locale.getDefault());

        c.setTime(new Date());
        slot.setSelectedDate(-1);

        slot.setToday(format.format(c.getTime()));

        c.add(Calendar.DATE, 1);
        slot.setTomorrow(format.format(c.getTime()));

        c.add(Calendar.DATE, 1);
        slot.setDayAfterTomorrow(format.format(c.getTime()));

        c.add(Calendar.DATE, 1);
        slot.setFourthDay(format.format(c.getTime()));

        c.add(Calendar.DATE, 1);
        slot.setFifthDay(format.format(c.getTime()));
        return slot;
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
            tvTotal.setText(String.format(Locale.getDefault(), format, Double.valueOf(twoDForm.format(value))));
        }

    }


    private void callAPIForShippingMethods() {
        mCurrentMethod = METHOD_GET_SHIPPING;
        UtilityClass.showProgressDialog(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(SlotFragment.this);
        manager.callAPIForFetchingShippingMethods(dbHandler.getSessionValue());
    }

    private void callAPIForPaymentMethods() {
        mCurrentMethod = METHOD_GET_PAYMENT;
        UtilityClass.showProgressDialog(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(SlotFragment.this);
        manager.callAPIForFetchingPaymentMethods(dbHandler.getSessionValue());
    }

    private void callAPIForShippingMethods(ShippingMethodRequest request) {
        mCurrentMethod = METHOD_POST_SHIPPING;
        UtilityClass.showProgressDialog(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(SlotFragment.this);
        manager.callAPIForAddingShippingMethods(dbHandler.getSessionValue(), request);
    }

    private void callAPIForPaymentMethods(String method) {
        PaymentMethodRequest request = new PaymentMethodRequest();
        request.setPaymentMethod(method);
        request.setAgree("1");
        request.setComment("String");

        mCurrentMethod = METHOD_POST_PAYMENT;
        UtilityClass.showProgressDialog(getContext());
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(SlotFragment.this);
        manager.callAPIForPostingPaymentMethods(dbHandler.getSessionValue(), request);
    }

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        if (response.body instanceof ShippingMethodResponse) {
            ShippingMethodResponse methodResponse = (ShippingMethodResponse) response.body;
            if (methodResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                callAPIForPaymentMethods();
            }
        } else if (response.body instanceof CommonResponse) {
            CommonResponse commonResponse = (CommonResponse) response.body;
            if (mCurrentMethod == METHOD_POST_SHIPPING) {
                if (commonResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                    Bridge paymentMethod = ((CheckoutActivity) getActivity()).getSelectedPaymentMethod();
                    if (paymentMethod != null) {
                        callAPIForPaymentMethods(((CheckoutActivity) getActivity()).getPaymentMethodCode());
                    } else {
                        showMessage("Please select a payment method");
                    }
                } else {
                    showMessage("Payment method sending failed");
                }
            } else if (mCurrentMethod == METHOD_POST_PAYMENT) {
                ((CheckoutActivity) getActivity()).goToNextPage();
//                callAPIForConfirmationPostMethods();
            }
        } else if (response.body instanceof PaymentMethodResponse) {
            PaymentMethodResponse paymentMethodResponse = (PaymentMethodResponse) response.body;
            if (paymentMethodResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                updatePaymentMethods(paymentMethodResponse);
            }
        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        showMessage(response.errorBody);
    }

    @Override
    public void noInternet() {
        UtilityClass.dismissProgressDialog();
        qa.happytots.yameenhome.components.Toast.makeToast(R.string.no_internet);
    }

    private void updatePaymentMethods(PaymentMethodResponse response) {
        PaymentMethods paymentOption = response.getData().getPaymentMethods();
        if (paymentOption.getCod() != null) {
            mRvItems.add(paymentOption.getCod());
            mAdapter.notifyItemInserted(mRvItems.size());
        }
    }

    private void showMessage(String message) {
        qa.happytots.yameenhome.components.Toast.makeToast(message);
    }

    private DeliverySlot getDeliverySlotDetails() {
        Iterator<Bridge> slots = mRvItems.iterator();
        while (slots.hasNext()) {
            Bridge bridge = slots.next();
            if (bridge instanceof DeliverySlot) {
                return (DeliverySlot) bridge;
            }
        }

        return null;
    }
}
