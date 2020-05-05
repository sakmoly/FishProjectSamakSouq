package qa.happytots.yameenhome.model.coupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class Coupon implements Bridge {

    @SerializedName("coupon_id")
    @Expose
    private Integer couponId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("shipping")
    @Expose
    private String shipping;
    @SerializedName("date_start")
    @Expose
    private String dateStart;
    @SerializedName("date_end")
    @Expose
    private String dateEnd;
    @SerializedName("uses_total")
    @Expose
    private String usesTotal;
    @SerializedName("uses_customer")
    @Expose
    private String usesCustomer;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("image")
    @Expose
    private Object image;

    private boolean isApplied = false;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getUsesTotal() {
        return usesTotal;
    }

    public void setUsesTotal(String usesTotal) {
        this.usesTotal = usesTotal;
    }

    public String getUsesCustomer() {
        return usesCustomer;
    }

    public void setUsesCustomer(String usesCustomer) {
        this.usesCustomer = usesCustomer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public boolean isApplied() {
        return isApplied;
    }

    public void setApplied(boolean applied) {
        isApplied = applied;
    }
}