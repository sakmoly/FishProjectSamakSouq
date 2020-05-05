package qa.happytots.yameenhome.view.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import qa.happytots.yameenhome.BuildConfig;
import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.YameenApplication;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.SocialLoginRequest;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.LoginRawClass;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.LoginResponseData;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.LoginResponse;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.login.AccountCustomField;
import qa.happytots.yameenhome.view.base.BaseActivity;

public class Login extends BaseActivity {

    private static final int RC_SIGN_IN = 0;

    private AppCompatButton login_btn;

    LinearLayout img_layout;
    int pass_image_status = 0;
    DatabaseHandler databaseHandler;
    TextInputEditText login_ed, password_ed;
    private TextView tvRegister;

    private ImageView ivGoogleSignIn;
    private ImageView ivFacebookSignIn;

    private LoginButton fbLoginBtn;

    private GoogleSignInClient mSignInClient;

    private CallbackManager mFbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        login_btn = findViewById(R.id.btn_login_submit);
        login_ed = findViewById(R.id.edt_login_email);
        password_ed = findViewById(R.id.edt_login_password);
        tvRegister = findViewById(R.id.tv_login_register);

        fbLoginBtn = findViewById(R.id.login_button);
        ivGoogleSignIn = findViewById(R.id.iv_google_sign_in);
        ivFacebookSignIn = findViewById(R.id.iv_facebook_sign_in);

        login_ed.addTextChangedListener(mTextWatcher);


        mFbManager = CallbackManager.Factory.create();
        fbLoginBtn.setReadPermissions(Arrays.asList("email"));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                .requestEmail()
                .requestProfile()
                .build();

        mSignInClient = GoogleSignIn.getClient(this, gso);

        databaseHandler = new DatabaseHandler(this);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterPage();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YameenApplication.hideKeyboardFrom(Login.this, view);
                validate();
            }
        });

        ivGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        ivFacebookSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLoginBtn.performClick();
            }
        });

        LoginManager.getInstance().registerCallback(mFbManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getUserProfile(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Login.this, "Facebook Login cancelled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(Login.this, "Facebook Login Failed ", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isDigitsOnly(s) && s.length() > 2) {
                String temp = "+966 " + s.toString();
                login_ed.removeTextChangedListener(mTextWatcher);
                login_ed.setText(temp);
                login_ed.setSelection(temp.length());
                login_ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(14)});
                login_ed.addTextChangedListener(mTextWatcher);
            } else if (s.toString().contains("+966") && s.length() > 2) {
                String temp = s.toString().substring(4);
                if (TextUtils.isDigitsOnly(temp)) {
                    login_ed.removeTextChangedListener(mTextWatcher);
                    login_ed.setText(temp);
                    login_ed.setSelection(temp.length());
                    login_ed.setFilters(new InputFilter[] {});
                    login_ed.addTextChangedListener(mTextWatcher);
                }
            } else {
                login_ed.setFilters(new InputFilter[] {});
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home || id == R.id.action_skip) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void validate() {
        String username = login_ed.getText().toString();
        String password = password_ed.getText().toString();
        if (TextUtils.isEmpty(username)) {
            login_ed.setError("Enter valid username");
        } else if (TextUtils.isEmpty(password)) {
            password_ed.setError("Enter valid password");
        } else {
            UtilityClass.showProgressDialog(Login.this);
            LoginRawClass loginRawClass = new LoginRawClass();
            loginRawClass.setEmail(username);
            loginRawClass.setPassword(password);
            callLoginAPI(loginRawClass);
        }
    }

    private void callLoginAPI(LoginRawClass request) {
        DatabaseHandler handler = new DatabaseHandler(Login.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForLogin(handler.getSessionValue(), request);
    }

    @Override
    public void successResponse(qa.happytots.yameenhome.datamodel.network.Response response) {
        UtilityClass.dismissProgressDialog();
        LoginResponse login_response = (LoginResponse) response.body;
        if (login_response != null && login_response.getSuccess() == Constants.SUCCESS_RESPONSE) {
            LoginResponseData data = login_response.getData();

            PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_CUSTOMER_ID , data.getCustomerId());
            PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_GROUP_ID , data.getCustomerGroupId());
            PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_FIRST_NAME , data.getFirstname());
            PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_LAST_NAME , data.getLastname());
            PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_EMAIL , data.getEmail());
            PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_TELEPHONE , data.getTelephone());
            if (data.getAccountCustomField() != null) {
                AccountCustomField customField = data.getAccountCustomField();
                String countryCode = customField.get1();
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_USER_COUNTRY_ID, countryCode);
            }

            PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOGGED_STATUS, true);

            goToPreviousPage();
        } else {
            List<String> errors = login_response.getError();
            if (errors.size() > 0) {
                qa.happytots.yameenhome.components.Toast.makeToast(errors.get(0));
            }
        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        Gson gson = new GsonBuilder().create();
        try {
            LoginResponse login_response = gson.fromJson(response.errorBody, LoginResponse.class);
            qa.happytots.yameenhome.components.Toast.makeToast(login_response.getError().get(0));
        } catch (Exception e) {
            parseRsponse(response.errorBody);
        }
    }

    private void goToRegisterPage() {
        Intent intent = new Intent(Login.this, RegisterActivity.class);
        startActivityForResult(intent, 1000);
    }

    private void goToPreviousPage() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void signIn() {

        Intent signInIntent = mSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (accountTask.isSuccessful()) {
                try {
                    GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                    if (account != null) {
                        SocialLoginRequest request = new SocialLoginRequest();
                        request.setAccessToken(account.getIdToken());
                        request.setEmail(account.getEmail().trim());
                        request.setProvider("google");
                        callYameenLoginAPI(request);
                    } else {
                        Toast.makeText(Login.this, "Google Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                boolean registerStatus = data.getBooleanExtra(RegisterActivity.REGISTRATION_SUCCESS_RESULT, false);
                if (registerStatus) {
                    goToPreviousPage();
                }
            }
        } else {
            mFbManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void callYameenLoginAPI(SocialLoginRequest request) {

        UtilityClass.showProgressDialog(Login.this);
        DatabaseHandler handler = new DatabaseHandler(Login.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForSocialLogin(handler.getSessionValue(), request);
    }

    private void getUserProfile(final AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
//                            String first_name = object.getString("first_name");
//                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
//                            String id = object.getString("id");
//                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                            SocialLoginRequest request = new SocialLoginRequest();
                            request.setAccessToken(currentAccessToken.getToken());
                            request.setEmail(email);
                            request.setProvider("facebook");

                            callYameenLoginAPI(request);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void parseRsponse(String errorBody) {
        try {
            JSONObject object = new JSONObject(errorBody);
            JSONArray array = object.optJSONArray("error");
            String errorMessage = array.optString(0);
            showMessage(errorMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

