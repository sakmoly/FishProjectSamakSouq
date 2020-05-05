package qa.happytots.yameenhome.model.shipping.fetch.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingMethods {

    @SerializedName("flat")
    @Expose
    private Flat flat;

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

}