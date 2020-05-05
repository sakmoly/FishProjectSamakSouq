package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FishListCategory implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.id);
    }

    public FishListCategory() {
    }

    protected FishListCategory(Parcel in) {
        this.name = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<FishListCategory> CREATOR = new Parcelable.Creator<FishListCategory>() {
        @Override
        public FishListCategory createFromParcel(Parcel source) {
            return new FishListCategory(source);
        }

        @Override
        public FishListCategory[] newArray(int size) {
            return new FishListCategory[size];
        }
    };
}
