package qa.happytots.yameenhome.model.quickview.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuickViewCategoryResponse {

@SerializedName("success")
@Expose
private Integer success;
@SerializedName("error")
@Expose
private List<String> error = null;
@SerializedName("data")
@Expose
private List<QuickView> data = null;

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

public List<QuickView> getData() {
return data;
}

public void setData(List<QuickView> data) {
this.data = data;
}

}