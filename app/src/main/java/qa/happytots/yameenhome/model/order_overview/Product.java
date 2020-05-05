package qa.happytots.yameenhome.model.order_overview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class Product implements Bridge {

@SerializedName("key")
@Expose
private String key;
@SerializedName("product_id")
@Expose
private String productId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("model")
@Expose
private String model;
@SerializedName("option")
@Expose
private List<Option> option = null;
@SerializedName("recurring")
@Expose
private String recurring;
@SerializedName("image")
@Expose
private String image;
@SerializedName("quantity")
@Expose
private String quantity;
@SerializedName("subtract")
@Expose
private String subtract;
@SerializedName("price")
@Expose
private String price;
@SerializedName("total")
@Expose
private String total;
@SerializedName("href")
@Expose
private String href;

public String getKey() {
return key;
}

public void setKey(String key) {
this.key = key;
}

public String getProductId() {
return productId;
}

public void setProductId(String productId) {
this.productId = productId;
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

public String getRecurring() {
return recurring;
}

public void setRecurring(String recurring) {
this.recurring = recurring;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getQuantity() {
return quantity;
}

public void setQuantity(String quantity) {
this.quantity = quantity;
}

public String getSubtract() {
return subtract;
}

public void setSubtract(String subtract) {
this.subtract = subtract;
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

public String getHref() {
return href;
}

public void setHref(String href) {
this.href = href;
}

}