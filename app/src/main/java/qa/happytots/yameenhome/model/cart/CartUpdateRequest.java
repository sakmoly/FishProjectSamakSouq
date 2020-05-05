package qa.happytots.yameenhome.model.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartUpdateRequest {

@SerializedName("key")
@Expose
private String key;
@SerializedName("quantity")
@Expose
private String quantity;

public String getKey() {
return key;
}

public void setKey(String key) {
this.key = key;
}

public String getQuantity() {
return quantity;
}

public void setQuantity(String quantity) {
this.quantity = quantity;
}

}