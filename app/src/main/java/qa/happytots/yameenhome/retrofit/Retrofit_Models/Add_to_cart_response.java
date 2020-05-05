package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Add_to_cart_response {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("error")
    @Expose
    private List<Object> error = null;
    @SerializedName("data")
    @Expose
    private Add_to_cart_data data;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Object> getError() {
        return error;
    }

    public void setError(List<Object> error) {
        this.error = error;
    }

    public Add_to_cart_data getData() {
        return data;
    }

    public void setData(Add_to_cart_data data) {
        this.data = data;
    }


}
