
package qa.happytots.yameenhome.model.quickview.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListOptionvalue;

public class Option implements Parcelable {

    @SerializedName("product_option_id")
    @Expose
    private Integer productOptionId;
    @SerializedName("option_value")
    @Expose
    private List<FishListOptionvalue> optionValue = null;
    @SerializedName("option_id")
    @Expose
    private String optionId;
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

    public String  getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
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

    public Option() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.productOptionId);
        dest.writeList(this.optionValue);
        dest.writeString(this.optionId);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.value);
        dest.writeString(this.required);
    }

    protected Option(Parcel in) {
        this.productOptionId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.optionValue = new ArrayList<FishListOptionvalue>();
        in.readList(this.optionValue, FishListOptionvalue.class.getClassLoader());
        this.optionId = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.value = in.readString();
        this.required = in.readString();
    }

    public static final Creator<Option> CREATOR = new Creator<Option>() {
        @Override
        public Option createFromParcel(Parcel source) {
            return new Option(source);
        }

        @Override
        public Option[] newArray(int size) {
            return new Option[size];
        }
    };
}
