package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartResponseproductOption implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    public CartResponseproductOption() {
    }

    protected CartResponseproductOption(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<CartResponseproductOption> CREATOR = new Parcelable.Creator<CartResponseproductOption>() {
        @Override
        public CartResponseproductOption createFromParcel(Parcel source) {
            return new CartResponseproductOption(source);
        }

        @Override
        public CartResponseproductOption[] newArray(int size) {
            return new CartResponseproductOption[size];
        }
    };
}
