package qa.happytots.yameenhome.model.order.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class Order implements Bridge, Cloneable {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("products")
    @Expose
    private Integer products;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("currency_value")
    @Expose
    private String currencyValue;
    @SerializedName("total_raw")
    @Expose
    private String totalRaw;
    @SerializedName("timestamp")
    @Expose
    private long timestamp;
    @SerializedName("currency")
    @Expose
    private Currency currency;

    private boolean isCollapsed;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getProducts() {
        return products;
    }

    public void setProducts(Integer products) {
        this.products = products;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(String currencyValue) {
        this.currencyValue = currencyValue;
    }

    public String getTotalRaw() {
        return totalRaw;
    }

    public void setTotalRaw(String totalRaw) {
        this.totalRaw = totalRaw;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void setCollapsed(boolean collapsed) {
        isCollapsed = collapsed;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return (Order) super.clone();
    }
}