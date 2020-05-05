package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FishListOptions implements Parcelable {

    @SerializedName("product_option_id")
    @Expose
    private Integer productOptionId;
    @SerializedName("option_value")
    @Expose
    private List<FishListOptionvalue> optionValue = null;
    @SerializedName("option_id")
    @Expose
    private Integer optionId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("required")
    @Expose
    private String required;

    public Integer getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Integer productOptionId) {
        this.productOptionId = productOptionId;
    }

    public List<FishListOptionvalue> getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(List<FishListOptionvalue> optionValue) {
        this.optionValue = optionValue;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.productOptionId);
        dest.writeTypedList(this.optionValue);
        dest.writeValue(this.optionId);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.value);
        dest.writeString(this.required);
    }

    public FishListOptions() {
    }

    protected FishListOptions(Parcel in) {
        this.productOptionId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.optionValue = in.createTypedArrayList(FishListOptionvalue.CREATOR);
        this.optionId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.type = in.readString();
        this.value = in.readString();
        this.required = in.readString();
    }

    public static final Parcelable.Creator<FishListOptions> CREATOR = new Parcelable.Creator<FishListOptions>() {
        @Override
        public FishListOptions createFromParcel(Parcel source) {
            return new FishListOptions(source);
        }

        @Override
        public FishListOptions[] newArray(int size) {
            return new FishListOptions[size];
        }
    };
}
