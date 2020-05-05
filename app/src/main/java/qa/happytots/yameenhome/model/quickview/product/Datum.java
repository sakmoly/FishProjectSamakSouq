
package qa.happytots.yameenhome.model.quickview.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class Datum implements Parcelable, Bridge {

    public static final int LEFT_SIDE = 0;
    public static final int RIGHT_SIDE = 1;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("original_image")
    @Expose
    private String originalImage;
    @SerializedName("original_images")
    @Expose
    private List<String> originalImages = null;
    @SerializedName("price_excluding_tax")
    @Expose
    private String priceExcludingTax;
    @SerializedName("price_excluding_tax_formated")
    @Expose
    private String priceExcludingTaxFormated;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("price_formated")
    @Expose
    private String priceFormated;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("attribute_groups")
    @Expose
    private List<Object> attributeGroups = null;
    @SerializedName("special")
    @Expose
    private String special;
    @SerializedName("special_excluding_tax")
    @Expose
    private String specialExcludingTax;
    @SerializedName("special_excluding_tax_formated")
    @Expose
    private String specialExcludingTaxFormated;
    @SerializedName("special_formated")
    @Expose
    private String specialFormated;
    @SerializedName("special_start_date")
    @Expose
    private String specialStartDate;
    @SerializedName("special_end_date")
    @Expose
    private String specialEndDate;
    @SerializedName("discounts")
    @Expose
    private List<Object> discounts = null;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("minimum")
    @Expose
    private String minimum;
    @SerializedName("meta_title")
    @Expose
    private String metaTitle;
    @SerializedName("meta_description")
    @Expose
    private String metaDescription;
    @SerializedName("meta_keyword")
    @Expose
    private String metaKeyword;
    @SerializedName("seo_url")
    @Expose
    private String seoUrl;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("upc")
    @Expose
    private String upc;
    @SerializedName("ean")
    @Expose
    private String ean;
    @SerializedName("jan")
    @Expose
    private String jan;
    @SerializedName("isbn")
    @Expose
    private String isbn;
    @SerializedName("mpn")
    @Expose
    private String mpn;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("stock_status")
    @Expose
    private String stockStatus;
    @SerializedName("stock_status_id")
    @Expose
    private String stockStatusId;
    @SerializedName("manufacturer_id")
    @Expose
    private String manufacturerId;
    @SerializedName("tax_class_id")
    @Expose
    private String taxClassId;
    @SerializedName("date_available")
    @Expose
    private String dateAvailable;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("weight_class_id")
    @Expose
    private Integer weightClassId;
    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("length_class_id")
    @Expose
    private String lengthClassId;
    @SerializedName("subtract")
    @Expose
    private String subtract;
    @SerializedName("sort_order")
    @Expose
    private String sortOrder;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("viewed")
    @Expose
    private String viewed;
    @SerializedName("weight_class")
    @Expose
    private String weightClass;
    @SerializedName("length_class")
    @Expose
    private String lengthClass;
    @SerializedName("shipping")
    @Expose
    private String shipping;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    private boolean needDetailedView=false;
    private boolean addToCartStatus=false;
    private int side;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public List<String> getOriginalImages() {
        return originalImages;
    }

    public void setOriginalImages(List<String> originalImages) {
        this.originalImages = originalImages;
    }

    public String getPriceExcludingTax() {
        return priceExcludingTax;
    }

    public void setPriceExcludingTax(String priceExcludingTax) {
        this.priceExcludingTax = priceExcludingTax;
    }

    public String getPriceExcludingTaxFormated() {
        return priceExcludingTaxFormated;
    }

    public void setPriceExcludingTaxFormated(String priceExcludingTaxFormated) {
        this.priceExcludingTaxFormated = priceExcludingTaxFormated;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Object> getAttributeGroups() {
        return attributeGroups;
    }

    public void setAttributeGroups(List<Object> attributeGroups) {
        this.attributeGroups = attributeGroups;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getSpecialExcludingTax() {
        return specialExcludingTax;
    }

    public void setSpecialExcludingTax(String specialExcludingTax) {
        this.specialExcludingTax = specialExcludingTax;
    }

    public String getSpecialExcludingTaxFormated() {
        return specialExcludingTaxFormated;
    }

    public void setSpecialExcludingTaxFormated(String specialExcludingTaxFormated) {
        this.specialExcludingTaxFormated = specialExcludingTaxFormated;
    }

    public String getSpecialFormated() {
        return specialFormated;
    }

    public void setSpecialFormated(String specialFormated) {
        this.specialFormated = specialFormated;
    }

    public String getSpecialStartDate() {
        return specialStartDate;
    }

    public void setSpecialStartDate(String specialStartDate) {
        this.specialStartDate = specialStartDate;
    }

    public String getSpecialEndDate() {
        return specialEndDate;
    }

    public void setSpecialEndDate(String specialEndDate) {
        this.specialEndDate = specialEndDate;
    }

    public List<Object> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Object> discounts) {
        this.discounts = discounts;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaKeyword() {
        return metaKeyword;
    }

    public void setMetaKeyword(String metaKeyword) {
        this.metaKeyword = metaKeyword;
    }

    public String getSeoUrl() {
        return seoUrl;
    }

    public void setSeoUrl(String seoUrl) {
        this.seoUrl = seoUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getMpn() {
        return mpn;
    }

    public void setMpn(String mpn) {
        this.mpn = mpn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getStockStatusId() {
        return stockStatusId;
    }

    public void setStockStatusId(String stockStatusId) {
        this.stockStatusId = stockStatusId;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getTaxClassId() {
        return taxClassId;
    }

    public void setTaxClassId(String taxClassId) {
        this.taxClassId = taxClassId;
    }

    public String getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(String dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getWeightClassId() {
        return weightClassId;
    }

    public void setWeightClassId(Integer weightClassId) {
        this.weightClassId = weightClassId;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLengthClassId() {
        return lengthClassId;
    }

    public void setLengthClassId(String lengthClassId) {
        this.lengthClassId = lengthClassId;
    }

    public String getSubtract() {
        return subtract;
    }

    public void setSubtract(String subtract) {
        this.subtract = subtract;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getViewed() {
        return viewed;
    }

    public void setViewed(String viewed) {
        this.viewed = viewed;
    }

    public String getWeightClass() {
        return weightClass;
    }

    public void setWeightClass(String weightClass) {
        this.weightClass = weightClass;
    }

    public String getLengthClass() {
        return lengthClass;
    }

    public void setLengthClass(String lengthClass) {
        this.lengthClass = lengthClass;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isNeedDetailedView() {
        return needDetailedView;
    }

    public void setNeedDetailedView(boolean needDetailedView) {
        this.needDetailedView = needDetailedView;
    }

    public boolean isAddToCartStatus() {
        return addToCartStatus;
    }

    public void setAddToCartStatus(boolean addToCartStatus) {
        this.addToCartStatus = addToCartStatus;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public Datum() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.productId);
        dest.writeString(this.name);
        dest.writeString(this.manufacturer);
        dest.writeString(this.sku);
        dest.writeString(this.model);
        dest.writeString(this.image);
        dest.writeStringList(this.images);
        dest.writeString(this.originalImage);
        dest.writeStringList(this.originalImages);
        dest.writeString(this.priceExcludingTax);
        dest.writeString(this.priceExcludingTaxFormated);
        dest.writeString(this.price);
        dest.writeString(this.priceFormated);
        dest.writeString(this.rating);
        dest.writeString(this.description);
        dest.writeList(this.attributeGroups);
        dest.writeString(this.special);
        dest.writeString(this.specialExcludingTax);
        dest.writeString(this.specialExcludingTaxFormated);
        dest.writeString(this.specialFormated);
        dest.writeString(this.specialStartDate);
        dest.writeString(this.specialEndDate);
        dest.writeList(this.discounts);
        dest.writeTypedList(this.options);
        dest.writeString(this.minimum);
        dest.writeString(this.metaTitle);
        dest.writeString(this.metaDescription);
        dest.writeString(this.metaKeyword);
        dest.writeString(this.seoUrl);
        dest.writeString(this.tag);
        dest.writeString(this.upc);
        dest.writeString(this.ean);
        dest.writeString(this.jan);
        dest.writeString(this.isbn);
        dest.writeString(this.mpn);
        dest.writeString(this.location);
        dest.writeString(this.stockStatus);
        dest.writeString(this.stockStatusId);
        dest.writeString(this.manufacturerId);
        dest.writeString(this.taxClassId);
        dest.writeString(this.dateAvailable);
        dest.writeString(this.weight);
        dest.writeValue(this.weightClassId);
        dest.writeString(this.length);
        dest.writeString(this.width);
        dest.writeString(this.height);
        dest.writeString(this.lengthClassId);
        dest.writeString(this.subtract);
        dest.writeString(this.sortOrder);
        dest.writeString(this.status);
        dest.writeString(this.dateAdded);
        dest.writeString(this.dateModified);
        dest.writeString(this.viewed);
        dest.writeString(this.weightClass);
        dest.writeString(this.lengthClass);
        dest.writeString(this.shipping);
        dest.writeString(this.reward);
        dest.writeString(this.points);
        dest.writeString(this.quantity);
        dest.writeByte(this.needDetailedView ? (byte) 1 : (byte) 0);
        dest.writeByte(this.addToCartStatus ? (byte) 1 : (byte) 0);
        dest.writeInt(this.side);
    }

    protected Datum(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.productId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.manufacturer = in.readString();
        this.sku = in.readString();
        this.model = in.readString();
        this.image = in.readString();
        this.images = in.createStringArrayList();
        this.originalImage = in.readString();
        this.originalImages = in.createStringArrayList();
        this.priceExcludingTax = in.readString();
        this.priceExcludingTaxFormated = in.readString();
        this.price = in.readString();
        this.priceFormated = in.readString();
        this.rating = in.readString();
        this.description = in.readString();
        this.attributeGroups = new ArrayList<Object>();
        in.readList(this.attributeGroups, Object.class.getClassLoader());
        this.special = in.readString();
        this.specialExcludingTax = in.readString();
        this.specialExcludingTaxFormated = in.readString();
        this.specialFormated = in.readString();
        this.specialStartDate = in.readString();
        this.specialEndDate = in.readString();
        this.discounts = new ArrayList<Object>();
        in.readList(this.discounts, Object.class.getClassLoader());
        this.options = in.createTypedArrayList(Option.CREATOR);
        this.minimum = in.readString();
        this.metaTitle = in.readString();
        this.metaDescription = in.readString();
        this.metaKeyword = in.readString();
        this.seoUrl = in.readString();
        this.tag = in.readString();
        this.upc = in.readString();
        this.ean = in.readString();
        this.jan = in.readString();
        this.isbn = in.readString();
        this.mpn = in.readString();
        this.location = in.readString();
        this.stockStatus = in.readString();
        this.stockStatusId = in.readString();
        this.manufacturerId = in.readString();
        this.taxClassId = in.readString();
        this.dateAvailable = in.readString();
        this.weight = in.readString();
        this.weightClassId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.length = in.readString();
        this.width = in.readString();
        this.height = in.readString();
        this.lengthClassId = in.readString();
        this.subtract = in.readString();
        this.sortOrder = in.readString();
        this.status = in.readString();
        this.dateAdded = in.readString();
        this.dateModified = in.readString();
        this.viewed = in.readString();
        this.weightClass = in.readString();
        this.lengthClass = in.readString();
        this.shipping = in.readString();
        this.reward = in.readString();
        this.points = in.readString();
        this.quantity = in.readString();
        this.needDetailedView = in.readByte() != 0;
        this.addToCartStatus = in.readByte() != 0;
        this.side = in.readInt();
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel source) {
            return new Datum(source);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };
}
