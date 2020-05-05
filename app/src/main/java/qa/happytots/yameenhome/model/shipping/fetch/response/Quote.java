package qa.happytots.yameenhome.model.shipping.fetch.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quote {

@SerializedName("flat")
@Expose
private Flat_ flat;

public Flat_ getFlat() {
return flat;
}

public void setFlat(Flat_ flat) {
this.flat = flat;
}

}