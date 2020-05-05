package qa.happytots.yameenhome.model.address.payment.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentAddressResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("error")
    @Expose
    private List<String> error = null;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}