package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.model.OrderSuccessItem;
import qa.happytots.yameenhome.model.OrderSuccessTotal;
import qa.happytots.yameenhome.model.order.OrderSummary;
import qa.happytots.yameenhome.model.order_overview.Product;
import qa.happytots.yameenhome.model.order_overview.ProductDetailHeader;
import qa.happytots.yameenhome.model.order_overview.ShipmentAddress;
import qa.happytots.yameenhome.model.order_overview.Total;

public class OrderSummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SUCCEES = 0;
    private static final int VIEW_TYPE_PRODUCT_DETAIL_HEADER = 1;
    private static final int VIEW_TYPE_PRODUCT_DETAILS = 2;
    private static final int VIEW_TYPE_PRODUCT_TOTAL = 3;
    private static final int VIEW_TYPE_PRODUCT_SHIPMENT_ADDRESS = 4;
    private static final int VIEW_TYPE_ORDER_SUMMARY = 5;

    private List<Bridge> mSummaryItems;

    public OrderSummaryAdapter(List<Bridge> mSummaryItems) {
        this.mSummaryItems = mSummaryItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == VIEW_TYPE_SUCCEES) {
            View view = inflater.inflate(R.layout.layout_successfull_transaction, parent, false);
            return new OrderSuccessViewHolder(view);
        } else if (viewType == VIEW_TYPE_PRODUCT_DETAIL_HEADER) {
            View view = inflater.inflate(R.layout.single_product_detail_header, parent, false);
            return new ProductDetailHeaderViewHolder(view);
        } else if (viewType == VIEW_TYPE_PRODUCT_DETAILS) {
            View view = inflater.inflate(R.layout.single_product_detail, parent, false);
            return new ProductDetailViewHolder(view);
        } else if (viewType == VIEW_TYPE_PRODUCT_TOTAL) {
            View view = inflater.inflate(R.layout.single_cart_total_overview, parent, false);
            return new CartTotalViewHolder(view);
        } else if (viewType == VIEW_TYPE_ORDER_SUMMARY) {
            View view = inflater.inflate(R.layout.single_order_summary, parent, false);
            return new OrderSummaryViewHolder(view);
        } else if (viewType == VIEW_TYPE_PRODUCT_SHIPMENT_ADDRESS){
            View view = inflater.inflate(R.layout.single_address, parent, false);
            return new ShipmentAddressViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductDetailViewHolder) {
            ProductDetailViewHolder viewHolder = (ProductDetailViewHolder) holder;
            Product product = (Product) mSummaryItems.get(position);

            String QUANTITY_TEMPLATE = "%s KG";
            viewHolder.tvHeaderName.setText(product.getName());
            viewHolder.tvWeightValue.setText(String.format(QUANTITY_TEMPLATE, product.getQuantity()));
            viewHolder.tvFinishValue.setText(product.getOption().get(0).getValue());
            viewHolder.tvAmountValue.setText(product.getTotal());
        } else if (holder instanceof ShipmentAddressViewHolder) {
            ShipmentAddressViewHolder viewHolder = (ShipmentAddressViewHolder) holder;
            ShipmentAddress address = (ShipmentAddress) mSummaryItems.get(position);

            viewHolder.tvAddressName.setText(address.getName());
            viewHolder.tvAddressOne.setText(address.getAddress1());
            viewHolder.tvAddressTwo.setText(address.getAddress2());
        } else if (holder instanceof CartTotalViewHolder) {
            CartTotalViewHolder viewHolder = (CartTotalViewHolder) holder;
            OrderSuccessTotal totals = (OrderSuccessTotal) mSummaryItems.get(position);
            Total subTotal = totals.getmSubTotal();
            Total coupon = totals.getmCoupon();
            Total shipment = totals.getmShipmentTotal();
            Total total = totals.getmGrandTotal();
            if (subTotal != null) {
                viewHolder.tvSubTotal.setText(String.valueOf(subTotal.getText()));
            } else {
                viewHolder.tvSubTotal.setText(R.string.label_zero_aed);
            }

            if (coupon != null) {
                viewHolder.tvcoupon.setText(String.valueOf(coupon.getText()));
            } else {
                viewHolder.tvcoupon.setText(R.string.label_zero_aed);
            }

            if (shipment != null) {
                viewHolder.tvDeliveryCharge.setText(shipment.getText());
            } else {
                viewHolder.tvDeliveryCharge.setText(R.string.label_zero_aed);
            }

            if (total != null) {
                viewHolder.tvTotal.setText(String.valueOf(total.getText()));

                if (totals.isCOD()) {
                    viewHolder.tvDeliveryInfo.setText(R.string.label_payment_option_cod);
                } else {
                    viewHolder.tvDeliveryInfo.setText(R.string.label_payment_option_telr);
                }
                viewHolder.tvDeliveryInfo.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvTotal.setText(R.string.label_zero_aed);
                viewHolder.tvDeliveryInfo.setVisibility(View.GONE);
            }

        } else if (holder instanceof OrderSummaryViewHolder) {
            OrderSummaryViewHolder viewHolder = (OrderSummaryViewHolder) holder;
            OrderSummary summary = (OrderSummary) mSummaryItems.get(position);
            viewHolder.tvOrderId.setText(String.valueOf(summary.getOrderId()));
            viewHolder.tvDeliveryDate.setText(summary.getDate());
            viewHolder.tvSlot.setText(summary.getSlot());
        } else if (holder instanceof OrderSuccessViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mSummaryItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        Bridge bridge = mSummaryItems.get(position);
        if (bridge instanceof OrderSuccessItem) {
            return VIEW_TYPE_SUCCEES;
        } else if (bridge instanceof Product) {
            return VIEW_TYPE_PRODUCT_DETAILS;
        }  else if (bridge instanceof ProductDetailHeader) {
            return VIEW_TYPE_PRODUCT_DETAIL_HEADER;
        } else if (bridge instanceof OrderSuccessTotal) {
            return VIEW_TYPE_PRODUCT_TOTAL;
        } else if (bridge instanceof OrderSummary) {
            return VIEW_TYPE_ORDER_SUMMARY;
        } else {
            return VIEW_TYPE_PRODUCT_SHIPMENT_ADDRESS;
        }
    }

    class OrderSuccessViewHolder extends RecyclerView.ViewHolder {

        OrderSuccessViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ProductDetailHeaderViewHolder extends RecyclerView.ViewHolder {

        ProductDetailHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ProductDetailViewHolder extends RecyclerView.ViewHolder {

        private YameenTextView tvHeaderName;
        private YameenTextView tvWeightValue;
        private YameenTextView tvFinishValue;
        private YameenTextView tvAmountValue;

        ProductDetailViewHolder(View itemView) {
            super(itemView);
            tvHeaderName = itemView.findViewById(R.id.tv_product_detail_header);
            tvWeightValue = itemView.findViewById(R.id.tv_product_detail_weight_value);
            tvFinishValue = itemView.findViewById(R.id.tv_product_detail_finish_value);
            tvAmountValue = itemView.findViewById(R.id.tv_product_detail_amount_value);
        }
    }

    class ProductTotalViewHolder extends RecyclerView.ViewHolder {

        private YameenTextView tvShipmentTotal;
        private YameenTextView tvGrandTotal;

        public ProductTotalViewHolder(View itemView) {
            super(itemView);
            tvShipmentTotal = itemView.findViewById(R.id.tv_order_total_shipment_value);
            tvGrandTotal = itemView.findViewById(R.id.tv_order_total_grand_value);
        }
    }

    class ShipmentAddressViewHolder extends RecyclerView.ViewHolder {

        private YameenTextView tvAddressName;
        private YameenTextView tvAddressOne;
        private YameenTextView tvAddressTwo;

        ShipmentAddressViewHolder(View itemView) {
            super(itemView);
            tvAddressName = itemView.findViewById(R.id.tv_shipping_address_name);
            tvAddressOne = itemView.findViewById(R.id.tv_shipping_address_address_one);
            tvAddressTwo = itemView.findViewById(R.id.tv_shipping_address_address_two);
        }
    }

    class CartTotalViewHolder extends RecyclerView.ViewHolder {

        private YameenTextView tvSubTotal;
        private YameenTextView tvcoupon;
        private YameenTextView tvTotal;
        private YameenTextView tvDeliveryInfo;
        private YameenTextView tvDeliveryCharge;

        CartTotalViewHolder(View itemView) {
            super(itemView);
            tvSubTotal = itemView.findViewById(R.id.tv_cart_subtotal_value);
            tvcoupon = itemView.findViewById(R.id.tv_cart_coupon_value);
            tvTotal = itemView.findViewById(R.id.tv_cart_total_value);
            tvDeliveryInfo = itemView.findViewById(R.id.tv_cart_total_delivery_fee_info);
            tvDeliveryCharge = itemView.findViewById(R.id.tv_cart_delivery_value);
        }
    }

    class OrderSummaryViewHolder extends RecyclerView.ViewHolder {

        private YameenTextView tvOrderId;
        private YameenTextView tvDeliveryDate;
        private YameenTextView tvSlot;
        private Group group;

        OrderSummaryViewHolder(View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_summary_order_id_value);
            tvDeliveryDate = itemView.findViewById(R.id.tv_order_summary_delivery_date_value);
            tvSlot = itemView.findViewById(R.id.tv_order_summary_delivery_slot_value);
            group = itemView.findViewById(R.id.group);
            group.setVisibility(View.GONE);

        }
    }
}
