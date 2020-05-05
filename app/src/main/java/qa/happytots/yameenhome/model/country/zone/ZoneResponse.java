package qa.happytots.yameenhome.model.country.zone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZoneResponse {

@SerializedName("success")
@Expose
private Integer success;
@SerializedName("error")
@Expose
private List<Object> error = null;
@SerializedName("data")
@Expose
private CountryDetails data;

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

public CountryDetails getData() {
return data;
}

public void setData(CountryDetails data) {
this.data = data;
}

}