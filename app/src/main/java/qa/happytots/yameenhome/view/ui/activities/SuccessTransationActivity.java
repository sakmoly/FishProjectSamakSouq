package qa.happytots.yameenhome.view.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.response.status.StatusResponse;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.HomeActivity;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.DateUtil;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.CommonResponseWithData;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.OrderSuccessItem;
import qa.happytots.yameenhome.model.OrderSuccessTotal;
import qa.happytots.yameenhome.model.order.OrderSummary;
import qa.happytots.yameenhome.model.order_overview.Data;
import qa.happytots.yameenhome.model.order_overview.OrderOverviewResponse;
import qa.happytots.yameenhome.model.order_overview.ShipmentAddress;
import qa.happytots.yameenhome.model.order_overview.Total;
import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.view.adapter.OrderSummaryAdapter;
import qa.happytots.yameenhome.view.base.BaseActivity;

public class SuccessTransationActivity extends BaseActivity {

    public static final String BUNDLE_IS_COD = "is_cod";
    public static final String BUNDLE_SELECTED_DATE = "delviery_date";
    public static final String BUNDLE_SELECTED_SLOT = "delviery_slot";

    private static final int API_POST = 0;
    private static final int API_PUT = 1;

    private boolean isCOD;

    private int mCurrentAPI = 0;

    private YameenTextView tvDone;

    private List<Bridge> mSummaryItems;
    private OrderSummaryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successtransaction);

        tvDone = findViewById(R.id.btn_success_done);
        RecyclerView rvSummary = findViewById(R.id.rv_summary);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvSummary.setLayoutManager(manager);
        mSummaryItems = new ArrayList<>();
        mAdapter = new OrderSummaryAdapter(mSummaryItems);
        rvSummary.setAdapter(mAdapter);

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT, 0);
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_DELIVERY_DATE, "");
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_DELIVERY_SLOT, -1);
                Intent intent = new Intent(SuccessTransationActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        isCOD = getIntent().getBooleanExtra(BUNDLE_IS_COD, false);
        StatusResponse status = (StatusResponse) intent.getParcelableExtra(WebviewActivity.PAYMENT_RESPONSE);
        if (status != null) {

            TextView textView = (TextView)findViewById(R.id.text_payment_result);
            textView.setText(textView.getText() +" : " + status.getTrace());

            if(status.getAuth()!= null) {
                status.getAuth().getStatus();   // Authorisation status. A indicates an authorised transaction. H also indicates an authorised transaction, but where the transaction has been placed on hold. Any other value indicates that the request could not be processed.
                status.getAuth().getAvs();      /* Result of the AVS check:
                                            Y = AVS matched OK
                                            P = Partial match (for example, post-code only)
                                            N = AVS not matched
                                            X = AVS not checked
                                            E = Error, unable to check AVS */
                status.getAuth().getCode();     // If the transaction was authorised, this contains the authorisation code from the card issuer. Otherwise it contains a code indicating why the transaction could not be processed.
                status.getAuth().getMessage();  // The authorisation or processing error message.
                status.getAuth().getCa_valid();
                status.getAuth().getCardcode(); // Code to indicate the card type used in the transaction. See the code list at the end of the document for a list of card codes.
                status.getAuth().getCardlast4();// The last 4 digits of the card number used in the transaction. This is supplied for all payment types (including the Hosted Payment Page method) except for PayPal.
                status.getAuth().getCvv();      /* Result of the CVV check:
                                           Y = CVV matched OK
                                           N = CVV not matched
                                           X = CVV not checked
                                           E = Error, unable to check CVV */
                status.getAuth().getTranref(); //The payment gateway transaction reference allocated to this request.
                Log.d("hany", status.getAuth().getTranref());
                status.getAuth().getCardfirst6(); // The first 6 digits of the card number used in the transaction, only for version 2 is submitted in Tran -> Version

                setTransactionDetails(status.getAuth().getTranref(), status.getAuth().getCardlast4());
            }
        }

        callAPIForConfirmationPostMethods();
    }

    private void setTransactionDetails(String ref, String last4) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("telr", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("ref", ref);
        editor.putString("last4", last4);
        editor.commit();
    }

    public void closeWindow(View view) {
        this.finish();
    }

    private void callAPIForConfirmationPostMethods() {
        mCurrentAPI = API_POST;

        String date = PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_DELIVERY_DATE);
        date = new DateUtil().convertToNewFormat("EEE-dd-MMMM-yyyy", "dd/MM/yyyy", date);
        String time;
        if (PreferenceController.getIntPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_DELIVERY_SLOT) == 1) {
            time = getString(R.string.label_slot_morning);
        } else {
            time = getString(R.string.label_slot_evening);
        }
        DatabaseHandler handler = new DatabaseHandler(SuccessTransationActivity.this);
        UtilityClass.showProgressDialog(SuccessTransationActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(SuccessTransationActivity.this);
        manager.callAPIForPostingConfirmation(handler.getSessionValue(), date, time);
    }

    private void callAPIForConfirmationPutMethods() {
        mCurrentAPI = API_PUT;
        DatabaseHandler handler = new DatabaseHandler(SuccessTransationActivity.this);
        UtilityClass.showProgressDialog(SuccessTransationActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForPutingConfirmation(handler.getSessionValue());
    }

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        if (response.body instanceof CommonResponseWithData) {
            CommonResponseWithData commonResponseWithData = (CommonResponseWithData) response.body;
            if (commonResponseWithData.getSuccess() == Constants.SUCCESS_RESPONSE) {
             //   qa.happytots.yameenhome.components.Toast.makeToast("Placed the order");
            }
        } else if (response.body instanceof OrderOverviewResponse) {
            OrderOverviewResponse overviewResponse = (OrderOverviewResponse) response.body;
            Data data = overviewResponse.getData();
            populateSummary(data);

            callAPIForConfirmationPutMethods();
        }
    }
    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
       // qa.happytots.yameenhome.components.Toast.makeToast(response.errorBody);
    }
    private void populateSummary(Data data) {

        OrderSuccessItem item = new OrderSuccessItem();
        mSummaryItems.add(item);

        OrderSummary summary = new OrderSummary();
        summary.setOrderId(data.getOrderId());
        summary.setDate(PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_DELIVERY_DATE));
        if (PreferenceController.getIntPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_DELIVERY_SLOT) == 1) {
            summary.setSlot(R.string.label_slot_morning);
        } else {
            summary.setSlot(R.string.label_slot_evening);
        }
        mSummaryItems.add(summary);

        mSummaryItems.addAll(data.getProducts());

        List<Total> totals = data.getTotals();
        OrderSuccessTotal totalSummary = new OrderSuccessTotal();
        for (Total total :totals) {
            if (total.getTitle().equals("Sub-Total")) {
                totalSummary.setmSubTotal(total);
            } else if (total.getTitle().equals("Total")) {
                totalSummary.setmGrandTotal(total);
            } else if (total.getTitle().contains("Coupon")) {
                totalSummary.setmCoupon(total);
            } else if (total.getTitle().equalsIgnoreCase("Flat Shipping Rate")) {
                totalSummary.setmShipmentTotal(total);
            }
        }
        totalSummary.setCOD(PreferenceController.getBooleanPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_PAYMENT_METHOD));
        totalSummary.setDeliveryFree(PreferenceController.getBooleanPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_IS_FREE_DELIVERY));
        mSummaryItems.add(totalSummary);

        ShipmentAddress address = new ShipmentAddress();
        address.setName(data.getFirstname() + " " + data.getLastname());
        address.setAddress1(data.getShippingCompany() + ", " + data.getShippingAddress1());
        address.setAddress2(data.getShippingCity() + ", " +
                data.getPaymentZone() + "-" + data.getShippingPostcode() + ", " +
                data.getShippingCountry());

        mSummaryItems.add(address);

        mAdapter.notifyDataSetChanged();
;    }

}
