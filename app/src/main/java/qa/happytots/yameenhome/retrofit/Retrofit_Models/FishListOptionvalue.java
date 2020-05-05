package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FishListOptionvalue implements Parcelable {

    boolean fishSellType_status = false;
    boolean fishquantityType_status = false;

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("price_formated")
    @Expose
    private String priceFormated;
    @SerializedName("price_excluding_tax")
    @Expose
    private Integer priceExcludingTax;
    @SerializedName("price_excluding_tax_formated")
    @Expose
    private String priceExcludingTaxFormated;
    @SerializedName("price_prefix")
    @Expose
    private String pricePrefix;
    @SerializedName("product_option_value_id")
    @Expose
    private Integer productOptionValueId;
    @SerializedName("option_value_id")
    @Expose
    private Integer optionValueId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPriceFormated() {
        return priceFormated;
    }

    public void setPriceFormated(String priceFormated) {
        this.priceFormated = priceFormated;
    }

    public Integer getPriceExcludingTax() {
        return priceExcludingTax;
    }

    public void setPriceExcludingTax(Integer priceExcludingTax) {
        this.priceExcludingTax = priceExcludingTax;
    }

    public String getPriceExcludingTaxFormated() {
        return priceExcludingTaxFormated;
    }

    public void setPriceExcludingTaxFormated(String priceExcludingTaxFormated) {
        this.priceExcludingTaxFormated = priceExcludingTaxFormated;
    }

    public String getPricePrefix() {
        return pricePrefix;
    }

    public void setPricePrefix(String pricePrefix) {
        this.pricePrefix = pricePrefix;
    }

    public Integer getProductOptionValueId() {
        return productOptionValueId;
    }

    public void setProductOptionValueId(Integer productOptionValueId) {
        this.productOptionValueId = productOptionValueId;
    }

    public Integer getOptionValueId() {
        return optionValueId;
    }

    public void setOptionValueId(Integer optionValueId) {
        this.optionValueId = optionValueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isFishSellType_status() {
        return fishSellType_status;
    }

    public void setFishSellType_status(boolean fishSellType_status) {
        this.fishSellType_status = fishSellType_status;
    }

    public boolean isFishquantityType_status() {
        return fishquantityType_status;
    }

    public void setFishquantityType_status(boolean fishquantityType_status) {
        this.fishquantityType_status = fishquantityType_status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.fishSellType_status ? (byte) 1 : (byte) 0);
        dest.writeByte(this.fishquantityType_status ? (byte) 1 : (byte) 0);
        dest.writeString(this.image);
        dest.writeValue(this.price);
        dest.writeString(this.priceFormated);
        dest.writeValue(this.priceExcludingTax);
        dest.writeString(this.priceExcludingTaxFormated);
        dest.writeString(this.pricePrefix);
        dest.writeValue(this.productOptionValueId);
        dest.writeValue(this.optionValueId);
        dest.writeString(this.name);
        dest.writeValue(this.quantity);
    }

    public FishListOptionvalue() {
    }

    protected FishListOptionvalue(Parcel in) {
        this.fishSellType_status = in.readByte() != 0;
        this.fishquantityType_status = in.readByte() != 0;
        this.image = in.readString();
        this.price = (Integer) in.readValue(Integer.class.getClassLoader());
        this.priceFormated = in.readString();
        this.priceExcludingTax = (Integer) in.readValue(Integer.class.getClassLoader());
        this.priceExcludingTaxFormated = in.readString();
        this.pricePrefix = in.readString();
        this.productOptionValueId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.optionValueId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<FishListOptionvalue> CREATOR = new Creator<FishListOptionvalue>() {
        @Override
        public FishListOptionvalue createFromParcel(Parcel source) {
            return new FishListOptionvalue(source);
        }

        @Override
        public FishListOptionvalue[] newArray(int size) {
            return new FishListOptionvalue[size];
        }
    };
}