package qa.happytots.yameenhome.model.payment.methods.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class PaymentMethods implements Bridge {

    @SerializedName("cod")
    @Expose
    private Cod cod;
    @SerializedName("telr")
    @Expose
    private Telr telr;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Cod getCod() {
        return cod;
    }

    public void setCod(Cod cod) {
        this.cod = cod;
    }

    public Telr getTelr() {
        return telr;
    }

    public void setTelr(Telr telr) {
        this.telr = telr;
    }


}