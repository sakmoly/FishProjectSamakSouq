package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Add_to_cart_data {

    @SerializedName("product")
    @Expose
    private Add_to_cart_data_product product;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_product_count")
    @Expose
    private Integer totalProductCount;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;

    public Add_to_cart_data_product getProduct() {
        return product;
    }

    public void setProduct(Add_to_cart_data_product product) {
        this.product = product;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getTotalProductCount() {
        return totalProductCount;
    }

    public void setTotalProductCount(Integer totalProductCount) {
        this.totalProductCount = totalProductCount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

}
