package qa.happytots.yameenhome.model.shipping.fetch.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("shipping_methods")
    @Expose
    private ShippingMethods shippingMethods;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("comment")
    @Expose
    private String comment;

    public ShippingMethods getShippingMethods() {
        return shippingMethods;
    }

    public void setShippingMethods(ShippingMethods shippingMethods) {
        this.shippingMethods = shippingMethods;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}