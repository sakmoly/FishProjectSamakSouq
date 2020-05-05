package qa.happytots.yameenhome.model.shipping.add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingMethodRequest {

    @SerializedName("shipping_method")
    @Expose
    private String shippingMethod;
    @SerializedName("comment")
    @Expose
    private String comment;

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}