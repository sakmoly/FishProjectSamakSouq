package qa.happytots.yameenhome.view.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.YameenEditText;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.CommonResponse;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.register.request.RegisterRequest;
import qa.happytots.yameenhome.model.register.response.Data;
import qa.happytots.yameenhome.model.register.response.RegisterResponse;

public class RegisterActivity extends AppCompatActivity implements NetworkManager.OnNetworkResponseListener {

    public static final String REGISTRATION_SUCCESS_RESULT = "registration_success";

    private YameenEditText edtFirstName;
    private YameenEditText edtLastName;
    private YameenEditText edtEmail;
    private YameenEditText edtPassword;
    private YameenEditText edtCPassword;
    private YameenEditText edtPhone;
    private YameenTextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar_register);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtFirstName = findViewById(R.id.edt_register_first_name);
        edtLastName = findViewById(R.id.edt_register_last_name);
        edtEmail = findViewById(R.id.edt_register_email);
        edtPassword = findViewById(R.id.edt_register_password);
        edtCPassword = findViewById(R.id.edt_register_c_password);
        edtPhone = findViewById(R.id.edt_register_phone);
        tvRegister = findViewById(R.id.tv_register);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
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

    private void registerUser() {
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String cPassword = edtCPassword.getText().toString();
        String phone = edtPhone.getText().toString();

        if (TextUtils.isEmpty(firstName)) {
            edtFirstName.setError("Field can not be empty");
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            edtLastName.setError("Field can not be empty");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Field can not be empty");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtFirstName.setError("Enter valid email id");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Field can not be empty");
            return;
        }

        if (TextUtils.isEmpty(cPassword)) {
            edtCPassword.setError("Field can not be empty");
            return;
        }

        if (!password.equals(cPassword)) {
            edtCPassword.setError("Password not matching");
        }

        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError("Field can not be empty");
            return;
        } else {
            if (phone.charAt(0) == '0') {
                edtPhone.setError("Mobile number can not start with 0");
                return;
            }
        }

        if (phone.length() != 9)  {
            edtPhone.setError("Enter valid phone number");
            return;
        }

        RegisterRequest request = new RegisterRequest();
        request.setFirstname(firstName);
        request.setLastname(lastName);
        request.setEmail(email);
        request.setPassword(password);
        request.setConfirm(cPassword);
        request.setTelephone(phone);
        request.setAgree("1");
        request.setCountryCode("966");

        UtilityClass.showProgressDialog(RegisterActivity.this);
        DatabaseHandler handler = new DatabaseHandler(RegisterActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForUserRegister(handler.getSessionValue(), request);

    }

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();

        if (response.body instanceof RegisterResponse) {
            RegisterResponse registerResponse = (RegisterResponse) response.body;
            if (registerResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                Data data = registerResponse.getData();
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_CUSTOMER_ID , data.getCustomerId() + "");
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_GROUP_ID , data.getCustomerGroupId());
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_FIRST_NAME , data.getFirstname());
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_LAST_NAME , data.getLastname());
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_EMAIL , data.getEmail());
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_TELEPHONE , data.getTelephone());

                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_STATUS, true);

                qa.happytots.yameenhome.components.Toast.makeToast(R.string.register_successfully);

                goToPreviousPage();
            } else {

                qa.happytots.yameenhome.components.Toast.makeToast(R.string.register_failed);
            }

        } else if (response.body instanceof CommonResponse) {
            CommonResponse response1 = (CommonResponse) response.body;
            if (response1.getSuccess() == Constants.SUCCESS_RESPONSE) {

            }
        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        qa.happytots.yameenhome.components.Toast.makeToast(R.string.register_failed);
    }

    @Override
    public void noInternet() {
        UtilityClass.dismissProgressDialog();
        qa.happytots.yameenhome.components.Toast.makeToast(R.string.no_internet);
    }

    private void goToOTPPage() {
        checkOTP("111111");
    }

    private void checkOTP(String otp) {

        UtilityClass.showProgressDialog(RegisterActivity.this);

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        DatabaseHandler handler = new DatabaseHandler(RegisterActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForOTPCheck(handler.getSessionValue(), otp, email, password);
    }

    private void goToPreviousPage() {
        Intent intent = new Intent();
        intent.putExtra(RegisterActivity.REGISTRATION_SUCCESS_RESULT, true);
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }
}
