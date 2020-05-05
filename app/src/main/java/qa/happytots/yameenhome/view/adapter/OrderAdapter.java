package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.model.order.detail.Product;
import qa.happytots.yameenhome.model.order.response.Order;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String ORDER_ID_TEMPLATE = "Order ID : %s";

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<Bridge> mOrders;

    private OnHeaderClickListener mListener;

    public OrderAdapter(List<Bridge> mOrders, OnHeaderClickListener mListener) {
        this.mOrders = mOrders;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == VIEW_TYPE_HEADER) {
            View view = inflater.inflate(R.layout.single_order_header, parent, false);
            return new OrderHeaderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.single_order_details, parent, false);
            return new OrderDetailViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderHeaderViewHolder) {
            OrderHeaderViewHolder viewHolder = (OrderHeaderViewHolder) holder;
            Order orderHeader = (Order) mOrders.get(position);
            viewHolder.tvOrderId.setText(String.format(ORDER_ID_TEMPLATE, orderHeader.getOrderId()));
            viewHolder.tvOrderDate.setText(orderHeader.getDateAdded());
            viewHolder.tvOrderStatus.setText("Status: "+ orderHeader.getStatus());
        } else {
            OrderDetailViewHolder viewHolder = (OrderDetailViewHolder) holder;
            Product product = (Product) mOrders.get(position);
            viewHolder.tvName.setText(product.getName());
            viewHolder.tvCurrency.setText(R.string.label_currency_aed);
            viewHolder.tvPrice.setText(String.format(Locale.getDefault(), "%.2f", Float.valueOf(product.getPriceRaw())));

            String urlTemplate = "https://yameen.ae/image/cache/catalog/Yameen_Fish/" + product.getName().replace(" ", "%20")+ "_tile-500x500.png";
            Log.d(OrderAdapter.class.getName(), "onBindViewHolder : " + urlTemplate);
            Picasso.get().load(urlTemplate)
                    .fit()
                    .error(R.drawable.default_placeholder)
                    .placeholder(R.drawable.default_placeholder)
                    .into(viewHolder.ivFish);
        }
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mOrders.get(position) instanceof Order) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    class OrderHeaderViewHolder extends RecyclerView.ViewHolder {

        private YameenTextView tvOrderId;
        private YameenTextView tvOrderDate;
        private YameenTextView tvOrderStatus;

        OrderHeaderViewHolder(View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onHeaderClick(getLayoutPosition());
                }
            });

            tvOrderId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order order = (Order) mOrders.get(getLayoutPosition());
                    mListener.onOrderClick(order.getOrderId());
                }
            });
        }
    }

    class OrderDetailViewHolder extends RecyclerView.ViewHolder {

        private YameenTextView tvCurrency;
        private YameenTextView tvPrice;
        private YameenTextView tvName;
        private AppCompatImageView ivFish;

        OrderDetailViewHolder(View itemView) {
            super(itemView);
            ivFish = itemView.findViewById(R.id.iv_order_fish);
            tvCurrency = itemView.findViewById(R.id.tv_order_currency);
            tvPrice = itemView.findViewById(R.id.tv_order_price);
            tvName = itemView.findViewById(R.id.tv_order_fish_name);
        }
    }

    public interface OnHeaderClickListener {
        void onHeaderClick(int position);
        void onOrderClick(String orderId);
    }
}
