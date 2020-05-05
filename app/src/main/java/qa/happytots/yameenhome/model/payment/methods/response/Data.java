package qa.happytots.yameenhome.model.payment.methods.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("payment_methods")
@Expose
private PaymentMethods paymentMethods;

public PaymentMethods getPaymentMethods() {
return paymentMethods;
}

public void setPaymentMethods(PaymentMethods paymentMethods) {
this.paymentMethods = paymentMethods;
}

}