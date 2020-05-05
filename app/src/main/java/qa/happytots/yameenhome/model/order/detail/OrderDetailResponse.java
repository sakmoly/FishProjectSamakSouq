package qa.happytots.yameenhome.model.order.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailResponse {

@SerializedName("success")
@Expose
private Integer success;
@SerializedName("error")
@Expose
private List<Object> error = null;
@SerializedName("data")
@Expose
private OrderDetail data;

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

public OrderDetail getData() {
return data;
}

public void setData(OrderDetail data) {
this.data = data;
}

}