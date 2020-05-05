package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewCart_Response {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("error")
    @Expose
    private List<Object> error = null;
//    @SerializedName("data")
//    @Expose
//    private List<New_Cart_Data> data;

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

//    public List<New_Cart_Data> getData() {
//        return data;
//    }
//
//    public void setData(List<New_Cart_Data> data) {
//        this.data = data;
//    }

}
