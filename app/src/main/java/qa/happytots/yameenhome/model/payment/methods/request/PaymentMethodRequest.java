package qa.happytots.yameenhome.model.payment.methods.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethodRequest {

    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("agree")
    @Expose
    private String agree;
    @SerializedName("comment")
    @Expose
    private String comment;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}