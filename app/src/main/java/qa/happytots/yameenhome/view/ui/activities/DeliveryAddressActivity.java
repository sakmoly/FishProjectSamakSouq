package qa.happytots.yameenhome.view.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.AlertUtil;
import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.view.adapter.DeliveryAddressAdapter;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.CommonResponse;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.delivery.DeliverySlot;
import qa.happytots.yameenhome.model.fetchaddress.Address;
import qa.happytots.yameenhome.model.fetchaddress.DeliveryAddressResponse;
import qa.happytots.yameenhome.view.base.BaseActivity;

public class DeliveryAddressActivity extends BaseActivity implements DeliveryAddressAdapter.OnAddressInteractionListener {

    private DeliveryAddressAdapter mAdapter;
    private List<Bridge> mAddress;

    private YameenTextView tvNoAddress;

    private int mDeletedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);

        Toolbar toolbar = findViewById(R.id.toolbar_address);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvNoAddress = findViewById(R.id.tv_no_address);

        RecyclerView rvAddress = findViewById(R.id.rv_address);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvAddress.setLayoutManager(manager);

        mAddress = new ArrayList<>();
        mAdapter = new DeliveryAddressAdapter(mAddress, this, true);
        rvAddress.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_address);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryAddressActivity.this, AddDeliveryAddressActivity.class);
                intent.putExtra(AddDeliveryAddressActivity.BUNDLE_FROM_CUSTOMER, true);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        callAPIForCustomerAddress();
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

    private void callAPIForCustomerAddress() {
        DatabaseHandler handler = new DatabaseHandler(DeliveryAddressActivity.this);
        UtilityClass.showProgressDialog(DeliveryAddressActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(DeliveryAddressActivity.this);
        manager.callAPIForGettingCustomerAddress(handler.getSessionValue());
    }

    private void callAPIForDeleteAddress(String addressId) {
        DatabaseHandler handler = new DatabaseHandler(DeliveryAddressActivity.this);
        UtilityClass.showProgressDialog(DeliveryAddressActivity.this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(DeliveryAddressActivity.this);
        manager.callAPIForDeletingCustomerAddress(handler.getSessionValue(), addressId);
    }

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        if (response.body instanceof DeliveryAddressResponse) {
            DeliveryAddressResponse addressResponse = (DeliveryAddressResponse) response.body;
            if (addressResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                mAddress.clear();
                mAddress.addAll(addressResponse.getData().getAddresses());
                if (mAddress.size() > 0) {
                    mAdapter.notifyDataSetChanged();
                    tvNoAddress.setVisibility(View.GONE);
                } else {
                    tvNoAddress.setVisibility(View.VISIBLE);
                }
            }
        } else if (response.body instanceof CommonResponse) {
            CommonResponse commonResponse = (CommonResponse) response.body;
            if (commonResponse.getSuccess() == Constants.SUCCESS_RESPONSE) {
                if (mDeletedPosition != -1) {
                    mAddress.remove(mDeletedPosition);
                    mAdapter.notifyItemRemoved(mDeletedPosition);
                    mDeletedPosition = -1;
                }
            }
        }
    }

    @Override
    public void edit(Address address) {
        Intent intent = new Intent(DeliveryAddressActivity.this, AddDeliveryAddressActivity.class);
        intent.putExtra(AddDeliveryAddressActivity.BUNDLE_FROM_CUSTOMER, true);
        intent.putExtra(AddDeliveryAddressActivity.BUNDLE_ADDRESS, address);
        startActivity(intent);
    }

    @Override
    public void onSlotSelect(int position, int selectedDate, DeliverySlot slot) {

    }

    @Override
    public void addressSelection(int position, boolean isChecked, Address address) {

    }

    @Override
    public void deleteAddress(int position, Address address) {
        showDeleteConfrmation(position, address);
    }

    private void showDeleteConfrmation(final int position, final Address address) {
        AlertUtil.makeAlert(DeliveryAddressActivity.this, "Are you sure do you want to delete the address?",
                "remove", "cancel", new AlertUtil.OnAlertInteractorListener() {
                    @Override
                    public void onPositiveClick() {
                        mDeletedPosition = position;
                        callAPIForDeleteAddress(address.getAddressId());
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
    }
}
