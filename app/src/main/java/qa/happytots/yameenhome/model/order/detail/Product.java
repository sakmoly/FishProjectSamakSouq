package qa.happytots.yameenhome.model.order.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class Product implements Bridge {

    @SerializedName("0")
    @Expose
    private String _0;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("order_product_id")
    @Expose
    private String orderProductId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("option")
    @Expose
    private List<Option> option = null;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("price_raw")
    @Expose
    private Integer priceRaw;
    @SerializedName("total_raw")
    @Expose
    private Integer totalRaw;
    @SerializedName("return")
    @Expose
    private String _return;

    public String get0() {
        return _0;
    }

    public void set0(String _0) {
        this._0 = _0;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Option> getOption() {
        return option;
    }

    public void setOption(List<Option> option) {
        this.option = option;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getPriceRaw() {
        return priceRaw;
    }

    public void setPriceRaw(Integer priceRaw) {
        this.priceRaw = priceRaw;
    }

    public Integer getTotalRaw() {
        return totalRaw;
    }

    public void setTotalRaw(Integer totalRaw) {
        this.totalRaw = totalRaw;
    }

    public String getReturn() {
        return _return;
    }

    public void setReturn(String _return) {
        this._return = _return;
    }

}