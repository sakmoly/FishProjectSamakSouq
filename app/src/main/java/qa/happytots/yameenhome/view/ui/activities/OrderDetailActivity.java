package qa.happytots.yameenhome.view.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.order.detail.OrderDetail;
import qa.happytots.yameenhome.model.order.detail.OrderDetailResponse;
import qa.happytots.yameenhome.model.order.detail.Product;
import qa.happytots.yameenhome.model.order_overview.OrderDetailTotal;
import qa.happytots.yameenhome.model.order_overview.OrderDetailTotalOg;
import qa.happytots.yameenhome.model.order_overview.ProductDetailHeader;
import qa.happytots.yameenhome.model.order_overview.ShipmentAddress;
import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.view.adapter.OrderDetailAdapter;
import qa.happytots.yameenhome.view.base.BaseActivity;

public class OrderDetailActivity extends BaseActivity {

    public static final String BUNDLE_ORDER_ID = "order_id";

    private List<Bridge> mOrderDetailItems;
    private OrderDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_my_order_detail);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView rvOrderDetails = findViewById(R.id.rv_order_details);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvOrderDetails.setLayoutManager(manager);


        String orderId = getIntent().getStringExtra(BUNDLE_ORDER_ID);
        toolbar.setTitle("Order ID - " + orderId);
        callAPIForOrderDetails(orderId);

        mOrderDetailItems = new ArrayList<>();
        mAdapter = new OrderDetailAdapter(mOrderDetailItems);
        rvOrderDetails.setAdapter(mAdapter);
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

    private void callAPIForOrderDetails(String orderId) {
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForOrderDetails(handler.getSessionValue(), orderId);
        UtilityClass.showProgressDialog(this);
    }

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
        OrderDetailResponse detailResponse = (OrderDetailResponse) response.body;
        populateOrderDetails(detailResponse);
    }

    private void populateOrderDetails(OrderDetailResponse detailResponse) {
        List<Product> products = detailResponse.getData().getProducts();
        if (products != null && products.size() > 0) {
            ProductDetailHeader header = new ProductDetailHeader();
            mOrderDetailItems.add(header);
            mOrderDetailItems.addAll(products);
        }

        List<OrderDetailTotalOg> totals = detailResponse.getData().getTotals();
        if (totals != null && totals.size() > 0) {
            OrderDetailTotal oTotal = new OrderDetailTotal();
            for (OrderDetailTotalOg total :totals) {
                if (total.getTitle().equals("Sub-Total")) {
                    oTotal.setSubTotal(total);
                } else if (total.getTitle().equals("Total")) {
                    oTotal.setGrandTotal(total);
                } else if (total.getTitle().contains("Coupon")) {
                    oTotal.setCoupon(total);
                } else if (total.getTitle().equalsIgnoreCase("Flat Shipping Rate")) {
                    oTotal.setShipmentTotal(total);
                }
            }

            mOrderDetailItems.add(oTotal);
        }

        ShipmentAddress address = new ShipmentAddress();
        OrderDetail detail = detailResponse.getData();
        address.setName(detail.getShippingFirstname() + " " + detail.getShippingLastname());
        address.setAddress1(detail.getShippingCompany() + ", " + detail.getShippingAddress1());
        address.setAddress2(detail.getShippingAddress2() + ", " + detail.getShippingCity()
                + ", " + detail.getPaymentZone() + " - " + detail.getShippingPostcode());
        mOrderDetailItems.add(address);

        mAdapter.notifyDataSetChanged();
    }
}
