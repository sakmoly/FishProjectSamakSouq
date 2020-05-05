package qa.happytots.yameenhome.model.order.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Total {

@SerializedName("order_total_id")
@Expose
private String orderTotalId;
@SerializedName("order_id")
@Expose
private String orderId;
@SerializedName("code")
@Expose
private String code;
@SerializedName("title")
@Expose
private String title;
@SerializedName("value")
@Expose
private String value;
@SerializedName("sort_order")
@Expose
private String sortOrder;

public String getOrderTotalId() {
return orderTotalId;
}

public void setOrderTotalId(String orderTotalId) {
this.orderTotalId = orderTotalId;
}

public String getOrderId() {
return orderId;
}

public void setOrderId(String orderId) {
this.orderId = orderId;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getValue() {
return value;
}

public void setValue(String value) {
this.value = value;
}

public String getSortOrder() {
return sortOrder;
}

public void setSortOrder(String sortOrder) {
this.sortOrder = sortOrder;
}

}