package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CartResponseData implements Parcelable {

    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("products")
    @Expose
    private List<CartResponseProduct> products = null;
    @SerializedName("vouchers")
    @Expose
    private List<Object> vouchers = null;
    @SerializedName("coupon_status")
    @Expose
    private String couponStatus;
    @SerializedName("coupon")
    @Expose
    private String coupon;
    @SerializedName("voucher_status")
    @Expose
    private String voucherStatus;
    @SerializedName("voucher")
    @Expose
    private String voucher;
    @SerializedName("reward_status")
    @Expose
    private Boolean rewardStatus;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("totals")
    @Expose
    private List<CartResponseTotal> totals = null;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_raw")
    @Expose
    private Double totalRaw;
    @SerializedName("total_product_count")
    @Expose
    private Double totalProductCount;
    @SerializedName("has_shipping")
    @Expose
    private Double hasShipping;
    @SerializedName("has_download")
    @Expose
    private Double hasDownload;
    @SerializedName("has_recurring_products")
    @Expose
    private Double hasRecurringProducts;
    @SerializedName("currency")
    @Expose
    private CartResponseCurrency currency;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<CartResponseProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartResponseProduct> products) {
        this.products = products;
    }

    public List<Object> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Object> vouchers) {
        this.vouchers = vouchers;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(String voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public Boolean getRewardStatus() {
        return rewardStatus;
    }

    public void setRewardStatus(Boolean rewardStatus) {
        this.rewardStatus = rewardStatus;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public List<CartResponseTotal> getTotals() {
        return totals;
    }

    public void setTotals(List<CartResponseTotal> totals) {
        this.totals = totals;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Double getTotalRaw() {
        return totalRaw;
    }

    public void setTotalRaw(Double totalRaw) {
        this.totalRaw = totalRaw;
    }

    public Double getTotalProductCount() {
        return totalProductCount;
    }

    public void setTotalProductCount(Double totalProductCount) {
        this.totalProductCount = totalProductCount;
    }

    public Double getHasShipping() {
        return hasShipping;
    }

    public void setHasShipping(Double hasShipping) {
        this.hasShipping = hasShipping;
    }

    public Double getHasDownload() {
        return hasDownload;
    }

    public void setHasDownload(Double hasDownload) {
        this.hasDownload = hasDownload;
    }

    public Double getHasRecurringProducts() {
        return hasRecurringProducts;
    }

    public void setHasRecurringProducts(Double hasRecurringProducts) {
        this.hasRecurringProducts = hasRecurringProducts;
    }

    public CartResponseCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(CartResponseCurrency currency) {
        this.currency = currency;
    }

    public CartResponseData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.weight);
        dest.writeTypedList(this.products);
        dest.writeList(this.vouchers);
        dest.writeString(this.couponStatus);
        dest.writeString(this.coupon);
        dest.writeString(this.voucherStatus);
        dest.writeString(this.voucher);
        dest.writeValue(this.rewardStatus);
        dest.writeString(this.reward);
        dest.writeTypedList(this.totals);
        dest.writeString(this.total);
        dest.writeValue(this.totalRaw);
        dest.writeValue(this.totalProductCount);
        dest.writeValue(this.hasShipping);
        dest.writeValue(this.hasDownload);
        dest.writeValue(this.hasRecurringProducts);
        dest.writeParcelable(this.currency, flags);
    }

    protected CartResponseData(Parcel in) {
        this.weight = in.readString();
        this.products = in.createTypedArrayList(CartResponseProduct.CREATOR);
        this.vouchers = new ArrayList<Object>();
        in.readList(this.vouchers, Object.class.getClassLoader());
        this.couponStatus = in.readString();
        this.coupon = in.readString();
        this.voucherStatus = in.readString();
        this.voucher = in.readString();
        this.rewardStatus = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.reward = in.readString();
        this.totals = in.createTypedArrayList(CartResponseTotal.CREATOR);
        this.total = in.readString();
        this.totalRaw = (Double) in.readValue(Double.class.getClassLoader());
        this.totalProductCount = (Double) in.readValue(Double.class.getClassLoader());
        this.hasShipping = (Double) in.readValue(Double.class.getClassLoader());
        this.hasDownload = (Double) in.readValue(Double.class.getClassLoader());
        this.hasRecurringProducts = (Double) in.readValue(Double.class.getClassLoader());
        this.currency = in.readParcelable(CartResponseCurrency.class.getClassLoader());
    }

    public static final Creator<CartResponseData> CREATOR = new Creator<CartResponseData>() {
        @Override
        public CartResponseData createFromParcel(Parcel source) {
            return new CartResponseData(source);
        }

        @Override
        public CartResponseData[] newArray(int size) {
            return new CartResponseData[size];
        }
    };
}
