package qa.happytots.yameenhome.model.cart.add.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("product")
@Expose
private Product product;
@SerializedName("total")
@Expose
private String total;
@SerializedName("total_product_count")
@Expose
private Integer totalProductCount;
@SerializedName("total_price")
@Expose
private String totalPrice;

public Product getProduct() {
return product;
}

public void setProduct(Product product) {
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