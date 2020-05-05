package qa.happytots.yameenhome.view.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.request.payment.App;
import com.telr.mobile.sdk.entity.request.payment.Billing;
import com.telr.mobile.sdk.entity.request.payment.MobileRequest;
import com.telr.mobile.sdk.entity.request.payment.Name;
import com.telr.mobile.sdk.entity.request.payment.Tran;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import qa.happytots.yameenhome.BuildConfig;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.YameenApplication;
import qa.happytots.yameenhome.components.StepView;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.model.delivery.DeliverySlot;
import qa.happytots.yameenhome.model.fetchaddress.Address;
import qa.happytots.yameenhome.model.payment.methods.response.Cod;
import qa.happytots.yameenhome.model.payment.methods.response.Telr;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseData;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseTotal;
import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.view.adapter.CheckoutPagerAdapter;

public class CheckoutActivity extends AppCompatActivity {

    public static final String BUNDLE_CART_DETAILS = "cart_details";

    public static final String KEY = "sPZXw~Q5cd@WsWkk";
    public static final String STORE_ID = "21583";
    public static final String EMAIL = "ameena.ali@yameen.ae";

    private Bridge mSelectedPaymentMethod;
    private DeliverySlot mSelectedDeliverySlot;
    private Address mSelectedAddress;
    private double mAmount = 0;


    public static final boolean isSecurityEnabled = false;   // Mark false to test on simulator, True to test on actual device and Production

    private ViewPager vpCheckout;
    private StepView svCheckout;

    private CartResponseData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = findViewById(R.id.toolbar_checkout);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        svCheckout = findViewById(R.id.sv_checkout);
        vpCheckout = findViewById(R.id.vp_checkout);
        vpCheckout.setOffscreenPageLimit(1);
        CheckoutPagerAdapter mVpAdapter = new CheckoutPagerAdapter(getSupportFragmentManager());
        vpCheckout.setAdapter(mVpAdapter);

        data = getIntent().getParcelableExtra(BUNDLE_CART_DETAILS);

        vpCheckout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        initSteps();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public CartResponseData getData() {
        return data;
    }

    public void setData(CartResponseData data) {
        this.data = data;
    }

    private void initSteps() {
        List<String> steps = new ArrayList<>();
        steps.add("Address");
        steps.add("Slot");
        steps.add("Order");

        svCheckout.setSteps(steps);
    }

    public void goToNextPage() {
        int currentPosition = vpCheckout.getCurrentItem();
        vpCheckout.setCurrentItem(currentPosition + 1);

        svCheckout.go(currentPosition + 1, true);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(CheckoutActivity.this, WebviewActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        intent.putExtra(WebviewActivity.EXTRA_MESSAGE, getMobileRequest());
        intent.putExtra(WebviewActivity.SUCCESS_ACTIVTY_CLASS_NAME, "qa.happytots.yameenhome.view.ui.activities.SuccessTransationActivity");
        intent.putExtra(WebviewActivity.FAILED_ACTIVTY_CLASS_NAME, "qa.happytots.yameenhome.view.ui.activities.FailedTransationActivity");
        intent.putExtra(WebviewActivity.IS_SECURITY_ENABLED, isSecurityEnabled);
        startActivity(intent);
    }

//    public void sendMessage2(View view) {
//        Intent intent = new Intent(CheckoutActivity.this, WebviewActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("telr", Context.MODE_PRIVATE);
//        String ref = sharedPref.getString("ref", null);
//        intent.putExtra(WebviewActivity.EXTRA_MESSAGE, getMobileRequestWithContAuth(ref));
//        intent.putExtra(WebviewActivity.SUCCESS_ACTIVTY_CLASS_NAME, "qa.happytots.yameenhome.view.ui.activities.SuccessTransationActivity");
//        intent.putExtra(WebviewActivity.FAILED_ACTIVTY_CLASS_NAME, "qa.happytots.yameenhome.view.ui.activities.FailedTransationActivity");
//        intent.putExtra(WebviewActivity.IS_SECURITY_ENABLED, isSecurityEnabled);
//        startActivity(intent);
//    }


    private MobileRequest getMobileRequest() {
        MobileRequest mobile = new MobileRequest();
        mobile.setStore(STORE_ID);                       // Store ID
        mobile.setKey(KEY);                              // Authentication Key : The Authentication Key will be supplied by Telr as part of the Mobile API setup process after you request that this integration type is enabled for your account. This should not be stored permanently within the App.
        App app = new App();
        app.setId("123456789");                          // Application installation ID
        app.setName(getString(R.string.app_name));                    // Application name
        app.setUser(String.valueOf(PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_CUSTOMER_ID)));                           // Application user ID : Your reference for the customer/user that is running the App. This should relate to their account within your systems.
        app.setVersion(BuildConfig.VERSION_NAME);                         // Application version
        app.setSdk("123");
        mobile.setApp(app);
        Tran tran = new Tran();
        tran.setTest("0");                              // Test mode : Test mode of zero indicates a live transaction. If this is set to any other value the transaction will be treated as a test.
        tran.setType("auth");                           /* Transaction type
                                                            'auth'   : Seek authorisation from the card issuer for the amount specified. If authorised, the funds will be reserved but will not be debited until such time as a corresponding capture command is made. This is sometimes known as pre-authorisation.
                                                            'sale'   : Immediate purchase request. This has the same effect as would be had by performing an auth transaction followed by a capture transaction for the full amount. No additional capture stage is required.
                                                            'verify' : Confirm that the card details given are valid. No funds are reserved or taken from the card.
                                                    */
        tran.setClazz("paypage");                       // Transaction class only 'paypage' is allowed on mobile, which means 'use the hosted payment page to capture and process the card details'
        tran.setCartid(String.valueOf(new BigInteger(128, new Random()))); //// Transaction cart ID : An example use of the cart ID field would be your own transaction or order reference.
        tran.setDescription(getString(R.string.app_name) + " Transaction at " + System.currentTimeMillis());         // Transaction description
        tran.setCurrency("SAR");                        // Transaction currency : Currency must be sent as a 3 character ISO code. A list of currency codes can be found at the end of this document. For voids or refunds, this must match the currency of the original transaction.
        tran.setAmount(String.valueOf(mAmount != 0 ? mAmount : data.getTotalRaw()));// Transaction amount : The transaction amount must be sent in major units, for example 9 dollars 50 cents must be sent as 9.50 not 950. There must be no currency symbol, and no thousands separators. Thedecimal part must be separated using a dot.
        //tran.setRef(???);                           // (Optinal) Previous transaction reference : The previous transaction reference is required for any continuous authority transaction. It must contain the reference that was supplied in the response for the original transaction.
        tran.setLangauge("en");                        // (Optinal) default is en -> English
        mobile.setTran(tran);
        Billing billing = new Billing();
        com.telr.mobile.sdk.entity.request.payment.Address address = new com.telr.mobile.sdk.entity.request.payment.Address();
        address.setCity(mSelectedAddress.getCity());                       // City : the minimum required details for a transaction to be processed
        address.setCountry("AE");                       // Country : Country must be sent as a 2 character ISO code. A list of country codes can be found at the end of this document. the minimum required details for a transaction to be processed
        address.setRegion(mSelectedAddress.getZone());                     // Region
        address.setLine1(mSelectedAddress.getAddress1());                 // Street address – line 1: the minimum required details for a transaction to be processed
        address.setLine2(mSelectedAddress.getAddress2());               // (Optinal)
        //address.setLine3("SIT G=Towe");               // (Optinal)
        //address.setZip("SIT G=Towe");                 // (Optinal)
        billing.setAddress(address);
        Name name = new Name();
        name.setFirst(PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_FIRST_NAME));                          // Forename : the minimum required details for a transaction to be processed
        name.setLast(PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_FIRST_NAME));                          // Surname : the minimum required details for a transaction to be processed
        name.setTitle("");                           // Title
        billing.setName(name);
        billing.setEmail(PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_EMAIL));                 // TODO: Insert your email here : the minimum required details for a transaction to be processed.
        billing.setPhone(PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_TELEPHONE));                // Phone number, required if enabled in your merchant dashboard.
        mobile.setBilling(billing);
        return mobile;

    }

//    /* This example used for continuous authority after using the first request, it used for recurring payment without asking the user to fill again the card details  */
//    private MobileRequest getMobileRequestWithContAuth(String ref) {
//        MobileRequest mobile = new MobileRequest();
//        mobile.setStore(STORE_ID);                       // Store ID
//        mobile.setKey(KEY);                              // Authentication Key : The Authentication Key will be supplied by Telr as part of the Mobile API setup process after you request that this integration type is enabled for your account. This should not be stored permanently within the App.
//        App app = new App();
//        app.setId("123456789");                          // Application installation ID
//        app.setName("Telr SDK DEMO");                    // Application name
//        app.setUser("123456");                           // Application user ID : Your reference for the customer/user that is running the App. This should relate to their account within your systems.
//        app.setVersion("0.0.1");                         // Application version
//        app.setSdk("123");
//        mobile.setApp(app);
//        Tran tran = new Tran();
//        tran.setTest("1");                              // Test mode : Test mode of zero indicates a live transaction. If this is set to any other value the transaction will be treated as a test.
//        tran.setType("sale");                           /* Transaction type
//                                                            'auth'   : Seek authorisation from the card issuer for the amount specified. If authorised, the funds will be reserved but will not be debited until such time as a corresponding capture command is made. This is sometimes known as pre-authorisation.
//                                                            'sale'   : Immediate purchase request. This has the same effect as would be had by performing an auth transaction followed by a capture transaction for the full amount. No additional capture stage is required.
//                                                            'verify' : Confirm that the card details given are valid. No funds are reserved or taken from the card.
//                                                        */
//        tran.setClazz("cont");
//        tran.setCartid(String.valueOf(new BigInteger(128, new Random()))); //// Transaction cart ID : An example use of the cart ID field would be your own transaction or order reference.
//        tran.setDescription("Test Mobile API");         // Transaction description
//        tran.setCurrency("AED");                        // Transaction currency : Currency must be sent as a 3 character ISO code. A list of currency codes can be found at the end of this document. For voids or refunds, this must match the currency of the original transaction.
//        tran.setAmount(amount);                         // Transaction amount : The transaction amount must be sent in major units, for example 9 dollars 50 cents must be sent as 9.50 not 950. There must be no currency symbol, and no thousands separators. Thedecimal part must be separated using a dot.
//        tran.setRef(ref);                        // TODO Previous transaction reference : The previous transaction reference is required for any continuous authority transaction. It must contain the reference that was supplied in the response for the original transaction.
//        mobile.setTran(tran);
//
//        return mobile;
//
//    }

    public Bridge getSelectedPaymentMethod() {
        return mSelectedPaymentMethod;
    }

    public void setSelectedPaymentMethod(Bridge mSelectedPaymentMethod) {
        this.mSelectedPaymentMethod = mSelectedPaymentMethod;
    }

    public DeliverySlot getSelectedDeliverySlot() {
        return mSelectedDeliverySlot;
    }

    public void setSelectedDeliverySlot(DeliverySlot mSelectedDeliverySlot) {
        PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_DELIVERY_DATE, YameenApplication.getSlot(mSelectedDeliverySlot.getSelectedDate()));
        PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_DELIVERY_SLOT, mSelectedDeliverySlot.getSelectedSlot());
        this.mSelectedDeliverySlot = mSelectedDeliverySlot;
    }

    public Address getSelectedAddress() {
        return mSelectedAddress;
    }

    public void setSelectedAddress(Address mSelectedAddress) {
        this.mSelectedAddress = mSelectedAddress;
    }

    public String getPaymentMethodCode() {
        String code;
        if (mSelectedPaymentMethod instanceof Cod) {
            code = ((Cod) mSelectedPaymentMethod).getCode();
        } else {
            code = ((Telr) mSelectedPaymentMethod).getCode();
        }
        return code;
    }

    public boolean isCOD() {
        if (mSelectedPaymentMethod instanceof Cod) {
            PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_PAYMENT_METHOD, true);
            return true;
        }
        return false;
    }

    public boolean isFreeDelivery() {
        List<CartResponseTotal> totals = data.getTotals();
        if (totals == null || totals.size() < 1) {
            return false;
        }
        CartResponseTotal cartSubTotal = totals.get(0);
        double total = data.getTotalRaw();
        if (cartSubTotal.getTitle().equals("Sub-Total")) {
            total = cartSubTotal.getValue();
        }
        PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_SELECTED_IS_FREE_DELIVERY, total >= 40);
        return total >= 40;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double mAmount) {
        this.mAmount = mAmount;
    }
}
