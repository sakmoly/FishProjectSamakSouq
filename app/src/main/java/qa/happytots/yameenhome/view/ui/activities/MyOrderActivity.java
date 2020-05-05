package qa.happytots.yameenhome.view.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.DateUtil;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.order.detail.OrderDetailResponse;
import qa.happytots.yameenhome.model.order.detail.Product;
import qa.happytots.yameenhome.model.order.response.Order;
import qa.happytots.yameenhome.model.order.response.OrderResponse;
import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.view.adapter.OrderAdapter;

public class MyOrderActivity extends AppCompatActivity implements NetworkManager.OnNetworkResponseListener,
        OrderAdapter.OnHeaderClickListener {

    private YameenTextView tvEmpty;

    private OrderAdapter mAdapter;
    private List<Bridge> mOrders;

    private int mHeaderPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        Toolbar toolbar = findViewById(R.id.toolbar_my_order);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        tvEmpty = findViewById(R.id.tv_order_empty);
        RecyclerView rvOrders = findViewById(R.id.rv_my_order);
        mOrders = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvOrders.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mOrders.get(position) instanceof Order) {
                    return 2;
                }
                return 1;
            }
        });

        Log.w("ORDER_SIZE", mOrders.size() + "");
        mAdapter = new OrderAdapter(mOrders, this);
        rvOrders.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        callForMyOrders();
    }

    private void callForMyOrders() {
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForOrders(handler.getSessionValue());
        UtilityClass.showProgressDialog(this);
        mOrders.clear();
    }

    private void callAPIForOrderDetails(String orderId) {
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForOrderDetails(handler.getSessionValue(), orderId);
        UtilityClass.showProgressDialog(this);
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
        if (response.body instanceof OrderResponse) {
            OrderResponse orderResponse = (OrderResponse) response.body;
            List<Order> orders = orderResponse.getData();
            populateOrderFromResponse(orders);
        } else {
            OrderDetailResponse detailResponse = (OrderDetailResponse) response.body;
            List<Product> products = detailResponse.getData().getProducts();
            if (mHeaderPosition != -1) {
                for (int i = 0, j = mHeaderPosition + 1; i < products.size(); i++, j++) {
                    mOrders.add(j, products.get(i));
                    mAdapter.notifyItemChanged(j);
                }
                mHeaderPosition = -1;
            }
        }
    }

    @Override
    public void failureResponse(Response response) {
        UtilityClass.dismissProgressDialog();
    }

    @Override
    public void noInternet() {
        UtilityClass.dismissProgressDialog();
    }

    private void populateOrderFromResponse(List<Order> orders) {
        if (orders != null) {
            DateUtil dateUtil = new DateUtil();
            for (Order order:
                 orders) {
                order.setCollapsed(true);
                String orderDate = dateUtil.convertToNewFormat("dd/MM/yyyy", "dd MMM YYYY", order.getDateAdded());
                order.setDateAdded(orderDate);
                mOrders.add(order);
            }
        }
        checkOrderEmpty();
    }

    private void checkOrderEmpty() {
        if (mOrders.size() > 0) {
            tvEmpty.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHeaderClick(int position) {
        mHeaderPosition = position;
        Order order = (Order) mOrders.get(position);
        if (order.isCollapsed()) {
            order.setCollapsed(false);
            callAPIForOrderDetails(order.getOrderId());
        } else {
            order.setCollapsed(true);
            collapse();
        }
    }

    @Override
    public void onOrderClick(String orderId) {
        Intent intent = new Intent(MyOrderActivity.this, OrderDetailActivity.class);
        intent.putExtra(OrderDetailActivity.BUNDLE_ORDER_ID, orderId);
        startActivity(intent);
    }

    private void collapse() {
        Iterator<Bridge> tempListItems = mOrders.subList(mHeaderPosition, mOrders.size() - 1).listIterator();
        while (tempListItems.hasNext()) {
           Bridge bridge = tempListItems.next();
            if (bridge instanceof Product) {
                tempListItems.remove();
            }
        }

        mAdapter.notifyDataSetChanged();
    }
}
