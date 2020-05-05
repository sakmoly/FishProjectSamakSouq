package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Session_TokenResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("error")
    @Expose
    private List<Object> error = null;
    @SerializedName("data")
    @Expose
    private SessionData data;

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

    public SessionData getData() {
        return data;
    }

    public void setData(SessionData data) {
        this.data = data;
    }


}
