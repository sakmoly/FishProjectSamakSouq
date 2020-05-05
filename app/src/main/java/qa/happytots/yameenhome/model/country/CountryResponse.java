package qa.happytots.yameenhome.model.country;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryResponse {

@SerializedName("success")
@Expose
private Integer success;
@SerializedName("error")
@Expose
private List<Object> error = null;
@SerializedName("data")
@Expose
private List<Country> data = null;

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

public List<Country> getData() {
return data;
}

public void setData(List<Country> data) {
this.data = data;
}

}