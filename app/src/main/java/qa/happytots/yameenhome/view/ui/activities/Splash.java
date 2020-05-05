package qa.happytots.yameenhome.view.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.HomeActivity;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.YameenApplication;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.SessionClass;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.Cart_Response;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.SessionData;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.Session_TokenResponse;
import qa.happytots.yameenhome.view.base.BaseActivity;

public class Splash extends BaseActivity {

    private DatabaseHandler handler;

    private final int API_SESSION = 0;
    private final int API_CART = 1;

    private int mCurrentAPI = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((YameenApplication)getApplication()).setLocale();
        setContentView(R.layout.activity_splash);

        AppCompatImageView imageView = findViewById(R.id.gif1);

        Glide.with(this).load(R.drawable.samak_souq_logo_2).into(imageView);
        handler = new DatabaseHandler(Splash.this);
        if (handler.getSessionValue().isEmpty() || handler.getSessionValue().equals("")) {
            callForSession();
        } else {
            callForCart();
        }
    }

    private void callForSession() {
        mCurrentAPI = API_SESSION;
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForSession();
    }

    private void callForCart() {
        mCurrentAPI = API_CART;
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForFetchingCarts(handler.getSessionValue());
    }

    @Override
    public void successResponse(Response response) {

        if (response.body instanceof Session_TokenResponse) {
            Session_TokenResponse sessionTokenResponse = (Session_TokenResponse) response.body;
            SessionData data = sessionTokenResponse.getData();
            handler.addSession(new SessionClass(data.getSession()));

            callForCart();
        } else {
            Cart_Response response1 = (Cart_Response) response.body;
            try {
                int count = response1.getData().getProducts().size();
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT, count);
            } catch (Exception e) {
                setCartCountToDefault();
                e.printStackTrace();
            }

            goToHome();
        }
    }

    @Override
    public void failureResponse(Response response) {
        if (mCurrentAPI == API_CART) {
            setCartCountToDefault();
            goToHome();
        } else {
            callForCart();
        }
    }

    private void setCartCountToDefault() {
        PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_CART_COUNT, 0);
    }

    private void goToHome() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String zoneId = PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOCATION_ID);
                if (zoneId.equals("")) {
                    Intent i = new Intent(Splash.this, LocationActivity.class);
                    i.putExtra(LocationActivity.BUNDLE_IS_FROM_SPLASH, true);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(Splash.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);
    }
}