package qa.happytots.yameenhome.view.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.HomeActivity;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.AlertUtil;
import qa.happytots.yameenhome.components.Toast;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.model.CartTotal;
import qa.happytots.yameenhome.model.CommonResponse;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.cart.CartUpdateRequest;
import qa.happytots.yameenhome.model.cart.CartUpdateResponse;
import qa.happytots.yameenhome.model.coupon.Coupon;
import qa.happytots.yameenhome.model.coupon.CouponHeader;
import qa.happytots.yameenhome.model.coupon.CouponManual;
import qa.happytots.yameenhome.model.coupon.CouponRequest;
import qa.happytots.yameenhome.model.coupon.CouponResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseData;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseProduct;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseTotal;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.Cart_Response;
import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.view.adapter.CartAdapter;
import qa.happytots.yameenhome.view.base.BaseActivity;

import static qa.happytots.yameenhome.view.ui.activities.CheckoutActivity.BUNDLE_CART_DETAILS;

public class CartActivity extends BaseActivity implements View.OnClickListener,
        CartAdapter.OnCartInteractionListener, NetworkManager.OnNetworkResponseListener {

    private static final int CART_FETCH = 0;
    private static final int CART_UPDATE = 1;
    private static final int CART_REMOVE = 2;
    private static final int COUPON_APPLY = 3;
    private static final int COUPON_REMOVE = 4;

    private static final String PRICE_TEMPLATE = "Total SAR : %.2f";
    private TextView tvCheckout;
    private TextView tvTotalAmount;
    private YameenTextView tvEmptyCart;
    private DatabaseHandler db;

    private CartResponseData data;
    private CartAdapter mAdapter;
    private List<Bridge> mProducts;

    private static int mCurrentAPIType = 0;

    private int mCouponHeaderPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView rvCartItems = findViewById(R.id.rv_cart_items);
        tvCheckout = findViewById(R.id.tv_check_out);
        tvTotalAmount = findViewById(R.id.cart_bottom_aed);
        tvEmptyCart = findViewById(R.id.tv_cart_empty);

        tvCheckout.setOnClickListener(this);
        tvTotalAmount.setOnClickListener(this);

        GridLayoutManager manager = new GridLayoutManager(this, 1);
        rvCartItems.setLayoutManager(manager);
        mProducts = new ArrayList<>();
        mAdapter = new CartAdapter(mProducts, this);
        rvCartItems.setAdapter(mAdapter);

        db = new DatabaseHandler(CartActivity.this);

        checkEmptyCart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        callCartApi();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == tvCheckout) {
            if (PreferenceController.isUsrLoggedIn()) {
                goToCheckOut();
            } else {
                showLoginAlertAndPage();
            }
        }
    }

    private void showStatusOfBottomButtons(boolean status) {
        if (status) {
            tvCheckout.setVisibility(View.VISIBLE);
            tvTotalAmount.setVisibility(View.VISIBLE);
        } else {
            tvCheckout.setVisibility(View.GONE);
            tvTotalAmount.setVisibility(View.GONE);
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(CartActivity.this, Login.class);
        startActivity(intent);
    }

    private void goToCheckOut() {
        Intent CheckoutIntent = new Intent(CartActivity.this, CheckoutActivity.class);
        if (data != null) {
            CheckoutIntent.putExtra(BUNDLE_CART_DETAILS, data);
            startActivity(CheckoutIntent);
        }
    }

    private void calculatePrice() {
        List<CartResponseTotal> totals = data.getTotals();
        try {
            CartResponseTotal total = totals.get(totals.size() - 1);
            DecimalFormat twoDForm = new DecimalFormat("#.##");
            tvTotalAmount.setText(String.format(Locale.getDefault(), PRICE_TEMPLATE, Double.valueOf(twoDForm.format(total.getValue()))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void decreaseClick(int position, CartResponseProduct product) {
        decreaseCart(product);
        if (mAdapter != null) {
            mAdapter.notifyItemChanged(position);
        }
        calculatePrice();
    }

    @Override
    public void increaseClick(int position, CartResponseProduct product) {
        increaseCart(product);
        if (mAdapter != null) {
            mAdapter.notifyItemChanged(position);
        }
        calculatePrice();
    }

    @Override
    public void removeClick(int position, CartResponseProduct product) {
        showAlertForRemove(product);
    }

    @Override
    public void cartExpand(CouponHeader header, int position) {
        if (PreferenceController.isUsrLoggedIn()) {
            if (!header.isExpanded()) {
                callCouponAPI();
                header.setExpanded(true);
            } else {
                removeCoupon();
                header.setExpanded(false);
            }
        } else {
            showLoginAlertAndPage();
        }

    }

    @Override
    public void onApplyCoupon(Coupon coupon) {
        if (coupon.isApplied()) {
            showRemoveCoupon();
        } else {
            CouponRequest request = new CouponRequest();
            request.setCoupon(coupon.getCode());
            applyCoupon(request);
        }
    }

    @Override
    public void onApplyCoupon(String couponCode) {
        CouponRequest request = new CouponRequest();
        request.setCoupon(couponCode);
        applyCoupon(request);
    }

    private void showLoginAlertAndPage() {
        Toast.makeToast(R.string.message_user_not_logged);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToLogin();
            }
        }, 1000);
    }
    private void showRemoveCoupon() {
        AlertUtil.makeAlert(CartActivity.this, getString(R.string.message_cart_remove),
                "Remove", "Cancel", new AlertUtil.OnAlertInteractorListener() {
                    @Override
                    public void onPositiveClick() {
                        callAPIForremoveCoupon();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
    }

    private void removeCoupon() {
        for (int i = mProducts.size() - 1; i > 0; i--) {
            Bridge bridge = mProducts.get(i);
            if (bridge instanceof Coupon || bridge instanceof CouponManual) {
                mProducts.remove(i);
                mAdapter.notifyItemRemoved(i);
            }
        }
    }

    private void decreaseCart(CartResponseProduct product) {
        int quantity = Integer.valueOf(product.getQuantity());
        if (quantity > 1) {
            quantity = quantity - 1;
        }
        product.setQuantity(String.valueOf(quantity));

        CartUpdateRequest request = new CartUpdateRequest();
        request.setKey(product.getKey());
        request.setQuantity(String.valueOf(quantity));
        updateCartQuantity(request);
    }

    private void increaseCart(CartResponseProduct product) {
        int quantity = Integer.valueOf(product.getQuantity());
        quantity = quantity + 1;
        product.setQuantity(String.valueOf(quantity));

        CartUpdateRequest request = new CartUpdateRequest();
        request.setKey(product.getKey());
        request.setQuantity(String.valueOf(quantity));
        updateCartQuantity(request);
    }

    private void callCartApi() {
        mCurrentAPIType = CART_FETCH;
        UtilityClass.showProgressDialog(CartActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForFetchingCarts(db.getSessionValue());
    }

    private void updateCartQuantity(CartUpdateRequest request) {
        mCurrentAPIType = CART_UPDATE;
        UtilityClass.showProgressDialog(CartActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForUpdatingCart(db.getSessionValue(), request);
    }

    private void deleteCart(CartResponseProduct product) {
        mCurrentAPIType = CART_REMOVE;
        UtilityClass.showProgressDialog(CartActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForRemoveFromCart(db.getSessionValue(), product.getKey());
    }

    private void callCouponAPI() {
        UtilityClass.showProgressDialog(CartActivity.this);
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForGettingCoupon(handler.getSessionValue());
    }

    private void applyCoupon(CouponRequest request) {
        mCurrentAPIType = COUPON_APPLY;
        UtilityClass.showProgressDialog(CartActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForApplyCoupon(db.getSessionValue(), request);
    }

    private void callAPIForremoveCoupon() {
        mCurrentAPIType = COUPON_REMOVE;
        UtilityClass.showProgressDialog(CartActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForRemoveCoupon(db.getSessionValue());
    }

    @Override
    public void successResponse(qa.happytots.yameenhome.datamodel.network.Response response) {
        UtilityClass.dismissProgressDialog();

        if (response.body instanceof Cart_Response) {
            Cart_Response res = (Cart_Response) response.body;
            if (res.getSuccess() == Constants.SUCCESS_RESPONSE) {
                if (mCurrentAPIType == CART_REMOVE) {
                    int count = PreferenceController.getIntPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT);
                    PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT, count - 1);
                    qa.happytots.yameenhome.components.Toast.makeToast(R.string.message_remove_cart);
                    callCartApi();
                } else {
                    data = res.getData();
                    if (res.getData().getProducts().size() > 0) {
                        mProducts.clear();
                        mProducts.addAll(res.getData().getProducts());
                        calculatePrice();
                        setHeader();
                        setTotalOverView();
                        checkEmptyCart();
                    } else {
                        finish();
                    }
                }
            }
        } else if (response.body instanceof CartUpdateResponse) {
            Toast.makeToast(R.string.message_update_cart);
            callCartApi();
        } else if (response.body instanceof CommonResponse) {
            CommonResponse cRes = (CommonResponse) response.body;
            if (cRes.getSuccess() == Constants.SUCCESS_RESPONSE) {
                if (mCurrentAPIType == COUPON_APPLY) {
                    Toast.makeToast(R.string.message_coupon_added);
                    removeCoupon();
                    mProducts.remove(mProducts.size() - 1);
                    mAdapter.notifyItemRemoved(mProducts.size() - 1);
                    callCartApi();
                } else if (mCurrentAPIType == COUPON_REMOVE) {
                    Toast.makeToast(R.string.message_coupon_removed);
                    removeCoupon();
                    mProducts.remove(mProducts.size() - 1);
                    mAdapter.notifyItemRemoved(mProducts.size() - 1);
                    callCartApi();
                } else if (mCurrentAPIType == CART_FETCH) {
                    checkEmptyCart();
                }
            }
        } else if (response.body instanceof CouponResponse) {
            CouponResponse cRes = (CouponResponse) response.body;
            if (cRes.getData() != null && cRes.getData().size() > 0) {
                setCoupons(cRes);
            }
        }
    }

    @Override
    public void failureResponse(qa.happytots.yameenhome.datamodel.network.Response response) {
        UtilityClass.dismissProgressDialog();
        if (mCurrentAPIType == COUPON_APPLY) {
            errorResponse(response.errorBody);
        } else {
            mProducts.clear();
            mAdapter.notifyDataSetChanged();
            checkEmptyCart();
            finish();
        }

    }

    private void errorResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optInt("success") == 0) {
                JSONArray jsonArray = jsonObject.optJSONArray("error");
                Toast.makeToast(jsonArray.optString(0));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeader() {
        CouponHeader header = new CouponHeader();
        mProducts.add(mProducts.size(), header);
        mAdapter.notifyDataSetChanged();
        mCouponHeaderPosition = mProducts.size();
    }

    private void setTotalOverView() {
        Iterator<CartResponseTotal> totals = data.getTotals().iterator();
        CartTotal total = new CartTotal();
        while (totals.hasNext()) {
            CartResponseTotal cTotal = totals.next();
            if (cTotal.getTitle().equals("Sub-Total")) {
                total.setSubTotal(cTotal);
                total.setDeliveryFree(cTotal.getValue() >= 40);
            } else if (cTotal.getTitle().equals("Total")) {
                total.setTotal(cTotal);
            } else if (cTotal.getTitle().contains("Coupon")) {
                total.setCoupon(cTotal);
            } else if (cTotal.getTitle().equalsIgnoreCase("Flat Shipping Rate")){
                total.setDelivery(cTotal);
            }
        }
        mProducts.add(total);
        mAdapter.notifyItemInserted(mProducts.size());
    }

    private void setCoupons(CouponResponse cRes) {
        String appliedCouponCode = null;
        if (data.getCouponStatus().equals("1")) {
            appliedCouponCode = data.getCoupon();
        }

        mProducts.add(mCouponHeaderPosition, new CouponManual());

        for (int i = 0, j = mCouponHeaderPosition + 1; i < cRes.getData().size(); i++, j++) {
            Coupon coupon = cRes.getData().get(i);
            if (appliedCouponCode != null && appliedCouponCode.equals(coupon.getCode())) {
                coupon.setApplied(true);
            }
            mProducts.add(j, coupon);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(HomeActivity.CART_COUNT, mProducts.size());
        setResult(RESULT_OK, intent);
        finish();
    }


    private void showAlertForRemove(final CartResponseProduct product) {
        AlertUtil.makeAlert(CartActivity.this,
                getString(R.string.messale_cart_remove),
                "Remove", "Cancel", new AlertUtil.OnAlertInteractorListener() {
            @Override
            public void onPositiveClick() {
                deleteCart(product);
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    private void checkEmptyCart() {
        if (mProducts.size() == 0) {
            tvEmptyCart.setVisibility(View.VISIBLE);
            showStatusOfBottomButtons(false);
        } else {
            tvEmptyCart.setVisibility(View.GONE);
            showStatusOfBottomButtons(true);
        }
    }
}