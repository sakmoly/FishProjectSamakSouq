package qa.happytots.yameenhome.model.payment.methods.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentExistingAddressRequest {

    @SerializedName("address_id")
    @Expose
    private String addressId;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

}