package qa.happytots.yameenhome;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.AlertUtil;
import qa.happytots.yameenhome.components.Toast;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.Cart_Response;
import qa.happytots.yameenhome.view.ui.activities.CartActivity;
import qa.happytots.yameenhome.view.ui.activities.QuickViewActivity;
import qa.happytots.yameenhome.view.ui.fragment.FishFragment;
import qa.happytots.yameenhome.view.ui.fragment.MeFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NetworkManager.OnNetworkResponseListener {

    public static final String CART_COUNT = "cart_count";

    private static final String HOME_FRAGMENT_TAG = "TAG_HOME";
    private static final String ME_FRAGMENT_TAG = "TAG_ME";

    private int height;
    private int width;
    private LinearLayout me_layout, home_layout;
    private ConstraintLayout cart_layout;
    private ImageView home_imageview, me_imageview, cart_imageview;
    private TextView me_textview, home_textview, cart_textview;
    private Toolbar toolBar;
    private DatabaseHandler databaseHandler;
    private AppCompatTextView tvCartBadge;
    private YameenTextView tvLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((YameenApplication) getApplication()).setLocale();
        setContentView(R.layout.activity_home);

        databaseHandler = new DatabaseHandler(this);

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        me_layout = findViewById(R.id.me_layout);
        home_layout = findViewById(R.id.home_layout);
        cart_layout = findViewById(R.id.cart_layout);

        home_imageview = findViewById(R.id.home_imageview);
        me_imageview = findViewById(R.id.me_imageview);
        cart_imageview = findViewById(R.id.cart_imageview);

        me_textview = findViewById(R.id.me_textview);
        home_textview = findViewById(R.id.home_textview);
        cart_textview = findViewById(R.id.cart_textview);

        home_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_selected));
        me_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar));
        cart_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_cart));
        me_textview.setTextColor(getResources().getColor(R.color.White));
        home_textview.setTextColor(getResources().getColor(R.color.Yellow));
        cart_textview.setTextColor(getResources().getColor(R.color.White));
        tvCartBadge = findViewById(R.id.tv_cart_badge);
        tvLanguage = findViewById(R.id.tv_language);


        FishFragment fragment = FishFragment.newInstance();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, HOME_FRAGMENT_TAG)
                .commit();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        setHeight(displayMetrics.heightPixels);
        setWidth(displayMetrics.widthPixels);


        me_layout.setOnClickListener(this);
        home_layout.setOnClickListener(this);
        cart_layout.setOnClickListener(this);
        tvLanguage.setOnClickListener(this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((YameenApplication) getApplication()).setLocale();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartCount();
    }

    public void updateCartCount() {
        int count = PreferenceController.getIntPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT);
        if (count == -1 || count == 0) {
            tvCartBadge.setVisibility(View.GONE);
        } else {
            tvCartBadge.setVisibility(View.VISIBLE);
            tvCartBadge.setText(String.valueOf(count));
        }
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.me_layout:

                MeFragment newFragment = MeFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, newFragment, ME_FRAGMENT_TAG)
                        .addToBackStack(ME_FRAGMENT_TAG)
                        .commit();

                // startActivity(new Intent(HomeActivity.this,Me.class));
                meLayoutClick();

                break;
            case R.id.home_layout:

                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HOME_FRAGMENT_TAG);
                if (fragment == null) {
                    fragment = FishFragment.newInstance();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, fragment, HOME_FRAGMENT_TAG)
                            .addToBackStack(HOME_FRAGMENT_TAG)
                            .commit();

                } else {
                    if (!fragment.isVisible()) {
                        getSupportFragmentManager().popBackStack();
                    }
                }


                homeLayoutClick();
                break;
            case R.id.cart_layout:
                int count = PreferenceController.getIntPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT);
                if (count == -1 || count == 0) {
                    Toast.makeToast("Cart is empty");
                } else {
                    goToCartPage();
                }
                break;

            case R.id.tv_language:
                changeLanguage();
                break;

        }
    }

    private void changeLanguage() {
        AlertUtil.makeLanguageAlert(HomeActivity.this, new AlertUtil.OnAlertInteractorListener() {
            @Override
            public void onPositiveClick() {
                tvLanguage.setText(R.string.label_language_english);
                finishMe();
            }

            @Override
            public void onNegativeClick() {
                tvLanguage.setText(R.string.label_language_arabic);
                finishMe();
            }
        });
    }

    private void cartLayoutClick() {
        tvLanguage.setVisibility(View.GONE);
        home_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
        me_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar));
        cart_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_cart_selected));
        me_textview.setTextColor(getResources().getColor(R.color.White));
        home_textview.setTextColor(getResources().getColor(R.color.White));
        cart_textview.setTextColor(getResources().getColor(R.color.Yellow));
    }

    private void homeLayoutClick() {
        tvLanguage.setVisibility(View.GONE);
        toolBar.setBackgroundColor(getResources().getColor(R.color.White));
        home_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_selected));
        me_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar));
        cart_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_cart));
        me_textview.setTextColor(getResources().getColor(R.color.White));
        home_textview.setTextColor(getResources().getColor(R.color.Yellow));
        cart_textview.setTextColor(getResources().getColor(R.color.White));
    }

    private void meLayoutClick() {
        tvLanguage.setVisibility(View.VISIBLE);
        toolBar.setBackgroundColor(getResources().getColor(R.color.lightYellow));
        home_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
        me_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar_selected));
        cart_imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_cart));
        me_textview.setTextColor(getResources().getColor(R.color.Yellow));
        home_textview.setTextColor(getResources().getColor(R.color.White));
        cart_textview.setTextColor(getResources().getColor(R.color.White));

        String language = PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_LANGUAGE);
        if (language.length() == 0 || language.equals("en")) {
            tvLanguage.setText(R.string.label_language_english);
        } else {
            tvLanguage.setText(R.string.label_language_arabic);
        }
    }

    public void callForCart() {
        DatabaseHandler handler = new DatabaseHandler(HomeActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForFetchingCarts(handler.getSessionValue());
    }

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        Cart_Response response1 = (Cart_Response) response.body;
        try {
            int count = response1.getData().getProducts().size();
            PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT, count);
            updateCartCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
    }

    @Override
    public void noInternet() {
        UtilityClass.dismissProgressDialog();
        Toast.makeToast(R.string.no_internet);
    }

    private void goToCartPage() {
        Intent intent = new Intent(HomeActivity.this, CartActivity.class);
        startActivity(intent);

        cartLayoutClick();

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HOME_FRAGMENT_TAG);
        if (fragment.isVisible()) {
            finish();
        } else {
            homeLayoutClick();
            super.onBackPressed();
        }
    }

    private void finishMe() {
        finish();
        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
