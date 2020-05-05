package qa.happytots.yameenhome.view.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.view.adapter.Service_Adapter;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.country.Country;
import qa.happytots.yameenhome.model.country.CountryResponse;

public class ServiceAvailableArea extends AppCompatActivity implements NetworkManager.OnNetworkResponseListener {

    private List<Country> list1;
    private Service_Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_available_area);


        Toolbar toolbar = findViewById(R.id.toolbar_country);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView rc1 = findViewById(R.id.rc1);
        list1 = new ArrayList<>();

        rc1.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Service_Adapter(list1);
        rc1.setHasFixedSize(true);
        rc1.setAdapter(mAdapter);

        callApiForCountryList();
    }

    private void callApiForCountryList() {
        UtilityClass.showProgressDialog(ServiceAvailableArea.this);
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForFetchingCountryList(handler.getSessionValue());
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
        CountryResponse countryResponse = (CountryResponse) response.body;
        if (countryResponse != null && countryResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
            list1.addAll(countryResponse.getData());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        Toast.makeText(ServiceAvailableArea.this, response.errorBody, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noInternet() {
        UtilityClass.dismissProgressDialog();
        Toast.makeText(ServiceAvailableArea.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
    }
}
