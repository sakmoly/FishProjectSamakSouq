package qa.happytots.yameenhome.view.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.country.Country;
import qa.happytots.yameenhome.model.country.CountryResponse;
import qa.happytots.yameenhome.model.country.zone.Zone;
import qa.happytots.yameenhome.model.country.zone.ZoneResponse;
import qa.happytots.yameenhome.model.fetchaddress.Address;
import qa.happytots.yameenhome.view.adapter.CountryAdapter;
import qa.happytots.yameenhome.view.adapter.ZoneAdapter;
import qa.happytots.yameenhome.view.ui.fragment.AddressFragment;

public class AddDeliveryAddressActivity extends AppCompatActivity implements NetworkManager.OnNetworkResponseListener {

    public static final String BUNDLE_ADDRESS = "address";
    public static final String BUNDLE_FROM_CUSTOMER = "from_customer";

    private static final int ADD_API = 0;
    private static final int EDIT_API = 1;

    private TextInputEditText edtFirstName;
    private TextInputEditText edtLastName;
    private TextInputEditText edtHouseNo;
    private TextInputEditText edtHouseName;
    private TextInputEditText edtStreetName;
    private TextInputEditText edtLandmark;
    private TextInputEditText edtPhoneNumber;
    private TextView tvSaveOrUpdate;
    private AppCompatSpinner spCountry;
    private AppCompatSpinner spAreaInsideCountry;

    private Address mAddress;

    private List<Country> mCountries;
    private CountryAdapter mCountryAdapter;
    private List<Zone> mZones;
    private ZoneAdapter mZoneAdapter;

    private boolean isFromCustomer = false;
    private int mCurrentAPI = 0;

    private int mSelectedCountyPosition = -1;
    private int mSelectedZonePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_address);

        Toolbar toolbar = findViewById(R.id.toolbar_delivery);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtFirstName = findViewById(R.id.edt_first_name);
        edtLastName = findViewById(R.id.edt_last_name);
        edtHouseNo = findViewById(R.id.edt_house_number);
        edtHouseName = findViewById(R.id.edt_house_name);
        edtStreetName = findViewById(R.id.edt_street_name);
        edtLandmark = findViewById(R.id.edt_landmark);
        edtPhoneNumber = findViewById(R.id.edt_phone);
        tvSaveOrUpdate = findViewById(R.id.tv_delivery_save);
        spCountry = findViewById(R.id.sp_country);
        spAreaInsideCountry = findViewById(R.id.sp_area_country);
        spAreaInsideCountry.setVisibility(View.GONE);

        isFromCustomer = getIntent().getBooleanExtra(BUNDLE_FROM_CUSTOMER, false);
        mAddress = getIntent().getParcelableExtra(BUNDLE_ADDRESS);
        if (mAddress != null) {
            setEditingData();
            tvSaveOrUpdate.setText(R.string.label_delivey_address_update);
        }

        tvSaveOrUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    if (isFromCustomer) {
                        if (mAddress != null) {
                            callEditCustomerAddress();
                        } else {
                            callAddCustomerAddress();
                        }
                    } else {
                        if (mAddress != null) {
                            callEditAddress();
                        } else {
                            callAddAddress();
                        }
                    }
                }
            }
        });

        mCountries = new ArrayList<>();
        mCountryAdapter = new CountryAdapter(mCountries);
        spCountry.setAdapter(mCountryAdapter);

        mZones = new ArrayList<>();
        mZoneAdapter = new ZoneAdapter(mZones);
        spAreaInsideCountry.setAdapter(mZoneAdapter);

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 0) {
//                    callApiForZoneList(String.valueOf(mCountries.get(position).getCountryId()));
//                }

                mSelectedCountyPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spAreaInsideCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    mSelectedZonePosition = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        callApiForCountryList();
    }

    private void addZoneInitValue() {
        Zone zone = new Zone();
        zone.setName("Select a zone");
        zone.setZoneId(String.valueOf(-1));
        mZones.add(zone);
    }

    private void setEditingData() {
        edtFirstName.setText(mAddress.getFirstname());
        edtLastName.setText(mAddress.getLastname());
        edtHouseNo.setText(mAddress.getCompany());
        edtHouseName.setText(mAddress.getAddress1());
        edtStreetName.setText(mAddress.getAddress2());
        edtLandmark.setText(mAddress.getCity());
        edtPhoneNumber.setText(mAddress.getPostcode());
    }

    private boolean validation() {
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String houseNo = edtHouseNo.getText().toString();
        String houseName = edtHouseName.getText().toString();
        String streetName = edtStreetName.getText().toString();
        String landmark = edtLandmark.getText().toString();
        String postcode = edtPhoneNumber.getText().toString();


        if (TextUtils.isEmpty(firstName)) {
            edtFirstName.setError("Field can not be empty");
            return false;
        }

        if (TextUtils.isEmpty(lastName)) {
            edtLastName.setError("Field can not be empty");
            return false;
        }

        if (TextUtils.isEmpty(houseNo)) {
            edtHouseNo.setError("Field can not be empty");
            return false;
        }

        if (TextUtils.isEmpty(houseName)) {
            edtHouseName.setError("Field can not be empty");
            return false;
        }

        if (TextUtils.isEmpty(streetName)) {
            edtStreetName.setError("Field can not be empty");
            return false;
        }

        if (TextUtils.isEmpty(landmark)) {
            edtLandmark.setError("Field can not be empty");
            return false;
        }

        if (TextUtils.isEmpty(postcode)) {
            edtPhoneNumber.setError("Field can not be empty");
            return false;
        }

        if (mSelectedCountyPosition == -1) {
            Toast.makeText(AddDeliveryAddressActivity.this, "Select a country", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (mSelectedZonePosition == -1) {
//            Toast.makeText(AddDeliveryAddressActivity.this, "Select a zone", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;

    }

    private Address makeAddressObject() {
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String houseNo = edtHouseNo.getText().toString();
        String houseName = edtHouseName.getText().toString();
        String streetName = edtStreetName.getText().toString();
        String landmark = edtLandmark.getText().toString();
        String postcode = edtPhoneNumber.getText().toString();

        Address address = new Address();
        address.setFirstname(firstName);
        address.setLastname(lastName);
        address.setCompany(houseNo);
        address.setAddress1("No :"+ houseName);
        address.setAddress2(streetName);
        address.setCity(landmark);
        address.setPostcode(postcode);
        address.setZoneId(PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOCATION_ID));

        if (mSelectedCountyPosition >= 0) {
            address.setCountryId(String.valueOf(mCountries.get(mSelectedCountyPosition).getCountryId()));
            address.setCountry(mCountries.get(mSelectedCountyPosition).getName());
        }

        if (mAddress != null) {
            address.setAddressId(mAddress.getAddressId());
        }

        return address;
    }

    private void callEditAddress() {
        mCurrentAPI = EDIT_API;
        UtilityClass.showProgressDialog(AddDeliveryAddressActivity.this);
        DatabaseHandler handler = new DatabaseHandler(AddDeliveryAddressActivity.this);
        Address address = makeAddressObject();
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForEditingAddress(handler.getSessionValue(), address);
    }

    private void callAddAddress() {
        mCurrentAPI = ADD_API;
        UtilityClass.showProgressDialog(AddDeliveryAddressActivity.this);
        DatabaseHandler handler = new DatabaseHandler(AddDeliveryAddressActivity.this);
        Address address = makeAddressObject();
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForAddingAddress(handler.getSessionValue(), address);
    }

    private void callAddCustomerAddress() {
        mCurrentAPI = ADD_API;
        UtilityClass.showProgressDialog(AddDeliveryAddressActivity.this);
        DatabaseHandler handler = new DatabaseHandler(AddDeliveryAddressActivity.this);
        Address address = makeAddressObject();
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForPosttingCustomerAddress(handler.getSessionValue(), address);
    }

    private void callEditCustomerAddress() {
        mCurrentAPI = EDIT_API;
        UtilityClass.showProgressDialog(AddDeliveryAddressActivity.this);
        DatabaseHandler handler = new DatabaseHandler(AddDeliveryAddressActivity.this);
        Address address = makeAddressObject();
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForPuttingCustomerAddress(handler.getSessionValue(), address);
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

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        if (response.body instanceof CountryResponse) {
            CountryResponse countryResponse = (CountryResponse) response.body;
            if (countryResponse != null && countryResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                mCountries.addAll(countryResponse.getData());
                mCountryAdapter.notifyDataSetChanged();
                getSelectedPosition();
            }
        } else if(response.body instanceof ZoneResponse) {
            ZoneResponse zoneResponse = (ZoneResponse) response.body;
            if (zoneResponse != null && zoneResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                mZones.clear();
                addZoneInitValue();
                mZones.addAll(zoneResponse.getData().getZone());
                mZoneAdapter.notifyDataSetChanged();
//                spAreaInsideCountry.setVisibility(View.VISIBLE);
                getSelectedZone();
            }
        } else {
            if (mCurrentAPI == ADD_API) {
                qa.happytots.yameenhome.components.Toast.makeToast("Successfully Added Address");
            } else {
                qa.happytots.yameenhome.components.Toast.makeToast("Successfully Modified Address");
            }
            Intent intent = new Intent();
            intent.putExtra(AddressFragment.ACTION_SUCCESS, true);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        try {
            JSONObject jsonObject = new JSONObject(response.errorBody);
            if (jsonObject.optInt("success") == 0) {
                JSONArray jsonArray = jsonObject.optJSONArray("error");
                qa.happytots.yameenhome.components.Toast.makeToast(jsonArray.optString(0));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void noInternet() {
        UtilityClass.dismissProgressDialog();
        qa.happytots.yameenhome.components.Toast.makeToast(R.string.no_internet);
    }

    private void callApiForCountryList() {
        UtilityClass.showProgressDialog(AddDeliveryAddressActivity.this);
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForFetchingCountryList(handler.getSessionValue());
    }

    private void callApiForZoneList(String countryId) {
        UtilityClass.showProgressDialog(AddDeliveryAddressActivity.this);
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForGettingZoneFromCountryId(handler.getSessionValue(), countryId);
    }

    private void getSelectedPosition() {
        if (mAddress != null) {
            int countryId = Integer.parseInt(mAddress.getCountryId());
            for (int i = 0; i < mCountries.size(); i++) {
                Country country = mCountries.get(i);
                if (country.getCountryId() == countryId) {
                    mSelectedCountyPosition = i;
                    spCountry.setSelection(i);
                }
            }
        }
    }

    private void getSelectedZone() {
        if (mAddress != null) {
            String zoneId = mAddress.getZoneId();
            for (int i = 0; i < mZones.size(); i++) {
                Zone zone = mZones.get(i);
                if (zone.getZoneId().equalsIgnoreCase(zoneId)) {
                    mSelectedZonePosition = i;
                    spAreaInsideCountry.setSelection(i);
                }
            }
        }
    }
}
