package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class CartResponseTotal implements Parcelable, Bridge {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private double value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public CartResponseTotal() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.text);
        dest.writeDouble(this.value);
    }

    protected CartResponseTotal(Parcel in) {
        this.title = in.readString();
        this.text = in.readString();
        this.value = in.readDouble();
    }

    public static final Creator<CartResponseTotal> CREATOR = new Creator<CartResponseTotal>() {
        @Override
        public CartResponseTotal createFromParcel(Parcel source) {
            return new CartResponseTotal(source);
        }

        @Override
        public CartResponseTotal[] newArray(int size) {
            return new CartResponseTotal[size];
        }
    };
}
