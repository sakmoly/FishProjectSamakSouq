package qa.happytots.yameenhome.view.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.coupon.Coupon;
import qa.happytots.yameenhome.model.coupon.CouponResponse;
import qa.happytots.yameenhome.view.adapter.CouponAdapter;
import qa.happytots.yameenhome.view.base.BaseActivity;

public class CouponActivity extends BaseActivity implements CouponAdapter.OnCouponSelectListener {


    public static final String BUNDLE_SELECTED_COUPON = "coupon_code";

    private List<Coupon> mCoupons;
    private CouponAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        Toolbar toolbar = findViewById(R.id.toolbar_coupon);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView rvCoupons = findViewById(R.id.rv_coupons);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvCoupons.setLayoutManager(manager);
        mCoupons = new ArrayList<>();
        mAdapter = new CouponAdapter(mCoupons, this);
        rvCoupons.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        callCouponAPI();
    }

    @Override
    public void onCouponSelect(Coupon coupon) {
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_SELECTED_COUPON, coupon.getCode());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void callCouponAPI() {
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForGettingCoupon(handler.getSessionValue());
    }

    @Override
    public void successResponse(Response response) {
        CouponResponse cRes = (CouponResponse) response.body;
        if (cRes != null) {
            mCoupons.clear();
            if (cRes.getData() != null && cRes.getData().size() > 0) {
                mCoupons.addAll(cRes.getData());
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
