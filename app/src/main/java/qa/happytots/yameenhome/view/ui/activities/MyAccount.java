package qa.happytots.yameenhome.view.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.Toast;
import qa.happytots.yameenhome.components.YameenEditText;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.CommonResponseWithData;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.account.AccountRequest;
import qa.happytots.yameenhome.model.country.Country;
import qa.happytots.yameenhome.model.country.CountryResponse;
import qa.happytots.yameenhome.model.register.response.Account;
import qa.happytots.yameenhome.model.register.response.CustomField;
import qa.happytots.yameenhome.model.user.Data;
import qa.happytots.yameenhome.view.adapter.CountryAdapter;
import qa.happytots.yameenhome.view.base.BaseActivity;

public class MyAccount extends BaseActivity implements NetworkManager.OnNetworkResponseListener {

    public static final String BUNDLE_ACCOUNT_DETAILS = "account_details";

    private YameenEditText edtName;
    private YameenEditText edtLastName;
    private YameenEditText edtMobileNo;
    private YameenEditText edtEmail;
    private AppCompatSpinner spCountry;

    private Data mAccountDetails;

    private List<Country> mCountries;
    private CountryAdapter mCountryAdapter;

    private int mSelectedCountryId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Toolbar toolbar = findViewById(R.id.toolbar_my_account);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtName = findViewById(R.id.edt_name);
        edtLastName = findViewById(R.id.edt_last_name);
        edtMobileNo = findViewById(R.id.edt_mobile_no);
        edtEmail = findViewById(R.id.edt_email);
        spCountry = findViewById(R.id.sp_country);

        mAccountDetails = getIntent().getParcelableExtra(BUNDLE_ACCOUNT_DETAILS);

        AppCompatButton btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        if (mAccountDetails != null) {
            setAccountDetials();
        }

        mCountries = new ArrayList<>();
        Country country = new Country();
        country.setName("Select a country");
        country.setCountryId(-1);
        mCountries.add(country);
        mCountryAdapter = new CountryAdapter(mCountries);
        spCountry.setAdapter(mCountryAdapter);

        callApiForCountryList();

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Country sCountry = mCountries.get(position);
                mSelectedCountryId = sCountry.getCountryId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAccountDetials() {
        edtName.setText(mAccountDetails.getFirstname());
        edtLastName.setText(mAccountDetails.getLastname());
        edtEmail.setText(mAccountDetails.getEmail());
        edtMobileNo.setText(mAccountDetails.getTelephone());
    }

    private void validate() {
        String name = edtName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String mobileNo = edtMobileNo.getText().toString();
        String email = edtEmail.getText().toString();

        if (TextUtils.isEmpty(name)) {
            edtName.setError("Fieled can not be empty");
        } else if (TextUtils.isEmpty(lastName)) {
            edtLastName.setError("Fieled can not be empty");
        } else if (TextUtils.isEmpty(mobileNo)) {
            edtMobileNo.setError("Fieled can not be empty");
        }  else if (mobileNo.length() != 9) {
            edtMobileNo.setError("Enter valid mobile no");
        } else if (mobileNo.charAt(0) == '0') {
            edtMobileNo.setError("Mobile no can not start with zero");
        } else if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Fieled can not be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Invalid email ID");
        } else {

            mAccountDetails.setFirstname(name);
            mAccountDetails.setLastname(lastName);
            mAccountDetails.setEmail(email);
            mAccountDetails.setTelephone(mobileNo);

            AccountRequest request = new AccountRequest();
            request.setFirstname(name);
            request.setLastname(lastName);
            request.setEmail(email);
            request.setTelephone(mobileNo);
            CustomField customField = new CustomField();
            Account account = new Account();
            account.set1(String.valueOf(mSelectedCountryId));
            customField.setAccount(account);
            request.setCustomField(customField);

            DatabaseHandler handler = new DatabaseHandler(MyAccount.this);
            NetworkManager manager = NetworkManager.getInstance();
            manager.setNetworkListener(this);
            manager.callAPIForUpdatingAccount(handler.getSessionValue(), request);
            UtilityClass.showProgressDialog(MyAccount.this);
        }
    }

    private void callApiForCountryList() {
        UtilityClass.showProgressDialog(MyAccount.this);
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForFetchingCountryList(handler.getSessionValue());
    }

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        if (response.body instanceof CommonResponseWithData) {
            CommonResponseWithData responseWithData = (CommonResponseWithData) response.body;
            if (responseWithData.getSuccess() == Constants.SUCCESS_RESPONSE) {
                Toast.makeToast("Account updated");
                Intent intent = new Intent();
                intent.putExtra(BUNDLE_ACCOUNT_DETAILS, mAccountDetails);
                setResult(RESULT_OK, intent);
                finish();
            }
        } else if (response.body instanceof CountryResponse) {
            CountryResponse countryResponse = (CountryResponse) response.body;
            if (countryResponse != null && countryResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                mCountries.addAll(countryResponse.getData());
                mCountryAdapter.notifyDataSetChanged();
                getUserCountry();
            }
        }
    }

    private void getUserCountry() {
        String cId = PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_USER_COUNTRY_ID);
        int id = Integer.parseInt(cId.equals("") ? "0" : cId);
        for (int i = 0; i < mCountries.size(); i++) {
            Country country = mCountries.get(i);
            if (country.getCountryId() == id) {
                mSelectedCountryId = id;
                spCountry.setSelection(mSelectedCountryId);
            }
        }
    }
}
