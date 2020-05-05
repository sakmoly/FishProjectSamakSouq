package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartResponseCurrency implements Parcelable {

    @SerializedName("currency_id")
    @Expose
    private String currencyId;
    @SerializedName("symbol_left")
    @Expose
    private String symbolLeft;
    @SerializedName("symbol_right")
    @Expose
    private String symbolRight;
    @SerializedName("decimal_place")
    @Expose
    private String decimalPlace;
    @SerializedName("value")
    @Expose
    private String value;

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getSymbolLeft() {
        return symbolLeft;
    }

    public void setSymbolLeft(String symbolLeft) {
        this.symbolLeft = symbolLeft;
    }

    public String getSymbolRight() {
        return symbolRight;
    }

    public void setSymbolRight(String symbolRight) {
        this.symbolRight = symbolRight;
    }

    public String getDecimalPlace() {
        return decimalPlace;
    }

    public void setDecimalPlace(String decimalPlace) {
        this.decimalPlace = decimalPlace;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.currencyId);
        dest.writeString(this.symbolLeft);
        dest.writeString(this.symbolRight);
        dest.writeString(this.decimalPlace);
        dest.writeString(this.value);
    }

    public CartResponseCurrency() {
    }

    protected CartResponseCurrency(Parcel in) {
        this.currencyId = in.readString();
        this.symbolLeft = in.readString();
        this.symbolRight = in.readString();
        this.decimalPlace = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<CartResponseCurrency> CREATOR = new Parcelable.Creator<CartResponseCurrency>() {
        @Override
        public CartResponseCurrency createFromParcel(Parcel source) {
            return new CartResponseCurrency(source);
        }

        @Override
        public CartResponseCurrency[] newArray(int size) {
            return new CartResponseCurrency[size];
        }
    };
}
