package qa.happytots.yameenhome.model.order.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {

@SerializedName("date_added")
@Expose
private String dateAdded;
@SerializedName("status")
@Expose
private String status;
@SerializedName("comment")
@Expose
private String comment;

public String getDateAdded() {
return dateAdded;
}

public void setDateAdded(String dateAdded) {
this.dateAdded = dateAdded;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getComment() {
return comment;
}

public void setComment(String comment) {
this.comment = comment;
}

}