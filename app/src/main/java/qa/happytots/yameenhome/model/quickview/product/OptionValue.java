
package qa.happytots.yameenhome.model.quickview.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptionValue implements Parcelable {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price")
    @Expose
    private String price;
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
    private String  productOptionValueId;
    @SerializedName("option_value_id")
    @Expose
    private Integer optionValueId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getProductOptionValueId() {
        return productOptionValueId;
    }

    public void setProductOptionValueId(String productOptionValueId) {
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public OptionValue() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeString(this.price);
        dest.writeString(this.priceFormated);
        dest.writeValue(this.priceExcludingTax);
        dest.writeString(this.priceExcludingTaxFormated);
        dest.writeString(this.pricePrefix);
        dest.writeString(this.productOptionValueId);
        dest.writeValue(this.optionValueId);
        dest.writeString(this.name);
        dest.writeString(this.quantity);
    }

    protected OptionValue(Parcel in) {
        this.image = in.readString();
        this.price = in.readString();
        this.priceFormated = in.readString();
        this.priceExcludingTax = (Integer) in.readValue(Integer.class.getClassLoader());
        this.priceExcludingTaxFormated = in.readString();
        this.pricePrefix = in.readString();
        this.productOptionValueId = in.readString();
        this.optionValueId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.quantity = in.readString();
    }

    public static final Creator<OptionValue> CREATOR = new Creator<OptionValue>() {
        @Override
        public OptionValue createFromParcel(Parcel source) {
            return new OptionValue(source);
        }

        @Override
        public OptionValue[] newArray(int size) {
            return new OptionValue[size];
        }
    };
}
