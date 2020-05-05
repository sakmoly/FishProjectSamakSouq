package qa.happytots.yameenhome.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.model.coupon.Coupon;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {

    private List<Coupon> mCoupons;
    private OnCouponSelectListener mListener;

    public CouponAdapter(List<Coupon> mCoupons, OnCouponSelectListener mListener) {
        this.mCoupons = mCoupons;
        this.mListener = mListener;
    }

    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_coupon, parent, false);
        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CouponViewHolder holder, int position) {
        Coupon coupon = mCoupons.get(position);
        String discount = "Discount : " + coupon.getDiscount();
        holder.tvDiscount.setText(discount);
        holder.tvCode.setText(coupon.getCode());
        String startDate = "Start Date : " + coupon.getDateStart();
        holder.tvStartDate.setText(startDate);
        String endDate = "End Date : " + coupon.getDateEnd();
        holder.tvEndDate.setText(endDate);
    }

    @Override
    public int getItemCount() {
        return mCoupons.size();
    }

    class CouponViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvCode;
        private AppCompatTextView tvDiscount;
        private AppCompatTextView tvStartDate;
        private AppCompatTextView tvEndDate;

        public CouponViewHolder(View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tv_coupon_code);
            tvDiscount = itemView.findViewById(R.id.tv_coupon_discount);

            tvCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onCouponSelect(mCoupons.get(getLayoutPosition()));
                }
            });

        }
    }

    public interface OnCouponSelectListener {
        void onCouponSelect(Coupon coupon);
    }
}
