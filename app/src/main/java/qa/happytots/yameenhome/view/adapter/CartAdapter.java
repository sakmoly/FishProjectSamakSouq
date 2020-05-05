package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.components.YameenEditText;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.model.CartTotal;
import qa.happytots.yameenhome.model.coupon.Coupon;
import qa.happytots.yameenhome.model.coupon.CouponHeader;
import qa.happytots.yameenhome.model.coupon.CouponManual;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseProduct;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseTotal;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseproductOption;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HOLDER_CART = 0;
    private static final int HOLDER_COUPON_HEADER = 1;
    private static final int HOLDER_COUPONS = 2;
    private static final int HOLDER_CART_TOTAL = 3;
    private static final int HOLDER_COUPON_MANUAL = 4;

    private final DecimalFormat twoDForm = new DecimalFormat("#.##");

    private List<Bridge> mCartList;
    private OnCartInteractionListener mListener;

    public CartAdapter(List<Bridge> cart, OnCartInteractionListener listener) {
        this.mCartList = cart;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (i == HOLDER_CART) {
            View view = inflater.inflate(R.layout.single_cart, viewGroup, false);
            return new CardHolder(view);
        } else if (i == HOLDER_COUPON_HEADER) {
            View view = inflater.inflate(R.layout.single_coupon_title, viewGroup, false);
            return new CouponHeaderViewHolder(view);
        } else if (i == HOLDER_CART_TOTAL) {
            View view = inflater.inflate(R.layout.single_cart_total_overview, viewGroup, false);
            return new CartTotalViewHolder(view);
        } else if (i == HOLDER_COUPON_MANUAL) {
            View view = inflater.inflate(R.layout.single_counpon_manual, viewGroup, false);
            return new CartCouponManualViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.single_coupon, viewGroup, false);
            return new CouponViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof CardHolder) {
            CardHolder holder = (CardHolder) viewHolder;
            CartResponseProduct product = (CartResponseProduct) mCartList.get(position);

            Picasso.get().load(product.getThumb())
                    .into(holder.ivThumb);

            holder.tvFishName.setText(product.getName());
            holder.tvFishType.setText(product.getModel());
            holder.tvFullName.setText(product.getModel());

            String quantity = product.getQuantity() + " KG";
            holder.tvQuantity.setText(quantity);

            double tot = product.getPriceRaw() * Float.parseFloat(product.getQuantity());
            String formatter = "%.2f";
            holder.tvPrice.setText(String.format(Locale.getDefault(), formatter, tot));
            List<CartResponseproductOption> options = product.getOption();
            if (options.size() > 0) {
                String finish = options.get(0).getValue();
                holder.tvFishType.setText(finish);

                switch (finish) {
                    case "Cleaned":
                        holder.ivFishFinish.setImageResource(R.drawable.ic_fish_1_new);
                        break;
                    case "Pellets":
                        holder.ivFishFinish.setImageResource(R.drawable.ic_fillets_new);
                        break;
                    default:
                        holder.ivFishFinish.setImageResource(R.drawable.ic_fish_2_new);
                        break;
                }
            }
        } else if (viewHolder instanceof CouponViewHolder) {
            CouponViewHolder holder = (CouponViewHolder) viewHolder;
            Coupon coupon = (Coupon) mCartList.get(position);
            holder.tvCode.setText(coupon.getCode());
            String discount = "Flat %s%% off ";
            holder.tvDiscount.setText(String.format(discount, Math.round(Float.valueOf(coupon.getDiscount()))));
            if (coupon.isApplied()) {
                holder.tvApply.setText(R.string.label_cart_coupon_applied);
            } else {
                holder.tvApply.setText(R.string.label_coupon_apply);
            }
        } else if (viewHolder instanceof CartTotalViewHolder) {
            CartTotalViewHolder holder = (CartTotalViewHolder) viewHolder;
            CartTotal totals = (CartTotal) mCartList.get(position);
            CartResponseTotal subTotal = totals.getSubTotal();
            CartResponseTotal coupon = totals.getCoupon();
            CartResponseTotal total = totals.getTotal();
            CartResponseTotal delivery = totals.getDelivery();

            if (subTotal != null) {
                holder.tvSubTotal.setText(String.valueOf(subTotal.getText()));

                if (totals.isDeliveryFree()) {
                    holder.tvDeliveryInfo.setText(R.string.label_free_delivery_eligible);
                } else {
                    String info = "You are SAR %.2f away from free delivery";
                    String result = twoDForm.format(40 - (subTotal.getValue()));
                    holder.tvDeliveryInfo.setText(String.format(info, Double.valueOf(result)));
                }

            } else {
                holder.tvSubTotal.setText(R.string.label_zero_aed);
            }

            if (coupon != null) {
                holder.tvcoupon.setText(String.valueOf(coupon.getText()));
            } else {
                holder.tvcoupon.setText(R.string.label_zero_aed);
            }

            if (delivery != null) {
                holder.tvDeliveryCharge.setText(delivery.getText());
                holder.groupDelivery.setVisibility(View.VISIBLE);
            } else {
                holder.groupDelivery.setVisibility(View.GONE);
            }

            if (total != null) {
                holder.tvTotal.setText(total.getText());
            } else {
                holder.tvTotal.setText(R.string.label_zero_aed);
            }

        } else if (viewHolder instanceof CartCouponManualViewHolder) {

        } else {
            CouponHeaderViewHolder headerViewHolder = (CouponHeaderViewHolder) viewHolder;
            CouponHeader header = (CouponHeader) mCartList.get(position);
            if (header.isExpanded()) {
                Drawable drawable = ContextCompat.getDrawable(headerViewHolder.tvHeader.getContext(), R.drawable.ic_left_arrow);
                headerViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            } else {
                Drawable drawable = ContextCompat.getDrawable(headerViewHolder.tvHeader.getContext(), R.drawable.ic_down_arrow);
                headerViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mCartList.get(position) instanceof CartResponseProduct) {
            return HOLDER_CART;
        } else if (mCartList.get(position) instanceof Coupon) {
            return HOLDER_COUPONS;
        } else if (mCartList.get(position) instanceof CartTotal) {
            return HOLDER_CART_TOTAL;
        } else if (mCartList.get(position) instanceof CouponManual) {
            return HOLDER_COUPON_MANUAL;
        } else {
            return HOLDER_COUPON_HEADER;
        }
    }

    class CardHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView ivThumb;
        private AppCompatImageView ivDecrease;
        private AppCompatImageView ivIncrease;
        private TextView tvFishName;
        private TextView tvFishType;
        private TextView tvPrice;
        private TextView tvQuantity;
        private YameenTextView tvFullName;
        private AppCompatImageView ivRemove;
        private AppCompatImageView ivFishFinish;

        CardHolder(View itemView) {
            super(itemView);

            ivThumb = itemView.findViewById(R.id.iv_fish_thumb);
            tvFishType = itemView.findViewById(R.id.tv_cart_fish_type);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivDecrease = itemView.findViewById(R.id.iv_cart_decrease_click);
            ivIncrease = itemView.findViewById(R.id.iv_cart_increase_click);
            tvFishName = itemView.findViewById(R.id.tv_cart_fish_name);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            ivRemove = itemView.findViewById(R.id.iv_cart_delete);
            tvFullName = itemView.findViewById(R.id.tv_cart_fish_full_name);
            ivFishFinish = itemView.findViewById(R.id.iv_cart_fish_finish);

            ivDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.decreaseClick(getLayoutPosition(), (CartResponseProduct) mCartList.get(getLayoutPosition()));
                }
            });

            ivIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.increaseClick(getLayoutPosition(), (CartResponseProduct) mCartList.get(getLayoutPosition()));
                }
            });

            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.removeClick(getLayoutPosition(), (CartResponseProduct) mCartList.get(getLayoutPosition()));
                }
            });
        }
    }

    class CouponHeaderViewHolder extends RecyclerView.ViewHolder {
        private YameenTextView tvHeader;

        CouponHeaderViewHolder(View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tv_coupon_header);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.cartExpand((CouponHeader) mCartList.get(getLayoutPosition()), getLayoutPosition());
                }
            });
        }
    }

    class CouponViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvCode;
        private AppCompatTextView tvDiscount;
        private AppCompatTextView tvApply;

        public CouponViewHolder(View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tv_coupon_code);
            tvDiscount = itemView.findViewById(R.id.tv_coupon_discount);
            tvApply = itemView.findViewById(R.id.tv_coupon_apply);

            tvApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onApplyCoupon((Coupon) mCartList.get(getLayoutPosition()));
                }
            });

        }
    }

    class CartTotalViewHolder extends RecyclerView.ViewHolder {

        private YameenTextView tvSubTotal;
        private YameenTextView tvcoupon;
        private YameenTextView tvTotal;
        private YameenTextView tvDeliveryInfo;
        private YameenTextView tvDeliveryCharge;
        private Group groupDelivery;

        CartTotalViewHolder(View itemView) {
            super(itemView);
            tvSubTotal = itemView.findViewById(R.id.tv_cart_subtotal_value);
            tvcoupon = itemView.findViewById(R.id.tv_cart_coupon_value);
            tvTotal = itemView.findViewById(R.id.tv_cart_total_value);
            tvDeliveryInfo = itemView.findViewById(R.id.tv_cart_total_delivery_fee_info);
            tvDeliveryCharge = itemView.findViewById(R.id.tv_cart_delivery_value);
            groupDelivery = itemView.findViewById(R.id.group_delivery);
        }
    }

    class CartCouponManualViewHolder extends RecyclerView.ViewHolder {

        private YameenEditText tvCode;
        private YameenTextView tvApply;
        CartCouponManualViewHolder(View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tv_coupon_code_manual);
            tvApply = itemView.findViewById(R.id.tv_coupon_apply_manual);

            tvApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = tvCode.getText().toString();

                    if (TextUtils.isEmpty(code)) {
                        mListener.showMessage("Please enter coupon code");
                    } else {
                        mListener.onApplyCoupon(code);
                    }
                }
            });
        }
    }


    public interface OnCartInteractionListener {
        void decreaseClick(int position, CartResponseProduct product);

        void increaseClick(int position, CartResponseProduct product);

        void removeClick(int position, CartResponseProduct product);

        void cartExpand(CouponHeader header, int position);

        void onApplyCoupon(Coupon coupon);

        void onApplyCoupon(String couponCode);

        void showMessage(String message);
    }
}
