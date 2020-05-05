package qa.happytots.yameenhome.view.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.OTPView;
import qa.happytots.yameenhome.components.Toast;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.CommonResponse;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.view.base.BaseActivity;

public class OTPActivity extends BaseActivity {

    public static final  String BUNDLE_EMAIL = "email_entered";
    public static final  String BUNDLE_PASSWORD = "password_entered";

    private OTPView otpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpView = findViewById(R.id.otp_view);

        YameenTextView btnSubmit = findViewById(R.id.tv_otp_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = otpView.getOTP();
                if (otp.length() == 6) {
                    checkOTP(otp);
                }
            }
        });

    }

    private void checkOTP(String otp) {

        String email = getIntent().getStringExtra(BUNDLE_EMAIL);
        String password = getIntent().getStringExtra(BUNDLE_PASSWORD);

        DatabaseHandler handler = new DatabaseHandler(OTPActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForOTPCheck(handler.getSessionValue(), otp, email, password);
    }

    @Override
    public void successResponse(Response response) {
        CommonResponse res = (CommonResponse) response.body;
        if (res.getSuccess() == Constants.SUCCESS_RESPONSE) {
            goToPreviousPage();
        } else {
            Toast.makeToast(res.getError().get(0));
        }
    }

    private void goToPreviousPage() {
        Intent intent = new Intent();
        intent.putExtra(RegisterActivity.REGISTRATION_SUCCESS_RESULT, true);
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }
}
