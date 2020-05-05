package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.model.IFishListItem;

public class FishListdata implements IFishListItem, Bridge, Parcelable {
    private boolean needDetailedView=false;
    private boolean addToCartStatus=false;

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
    private Object manufacturer;
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
    private double priceExcludingTax;
    @SerializedName("price_excluding_tax_formated")
    @Expose
    private String priceExcludingTaxFormated;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("price_formated")
    @Expose
    private String priceFormated;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("attribute_groups")
    @Expose
    private List<Object> attributeGroups = null;
    @SerializedName("special")
    @Expose
    private double special;
    @SerializedName("special_excluding_tax")
    @Expose
    private double specialExcludingTax;
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
    private List<FishListOptions> options = null;
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
    private Integer stockStatusId;
    @SerializedName("manufacturer_id")
    @Expose
    private Integer manufacturerId;
    @SerializedName("tax_class_id")
    @Expose
    private Integer taxClassId;
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
    private Integer lengthClassId;
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
    private Object reward;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("category")
    @Expose
    private List<FishListCategory> category = null;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("reviews")
    @Expose
    private FishListReview reviews;
    @SerializedName("recurrings")
    @Expose
    private List<Object> recurrings = null;

    private int side;

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

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

    public Object getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Object manufacturer) {
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


    public String getPriceExcludingTaxFormated() {
        return priceExcludingTaxFormated;
    }

    public void setPriceExcludingTaxFormated(String priceExcludingTaxFormated) {
        this.priceExcludingTaxFormated = priceExcludingTaxFormated;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
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

    public double getPriceExcludingTax() {
        return priceExcludingTax;
    }

    public void setPriceExcludingTax(double priceExcludingTax) {
        this.priceExcludingTax = priceExcludingTax;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSpecial() {
        return special;
    }

    public void setSpecial(double special) {
        this.special = special;
    }

    public double getSpecialExcludingTax() {
        return specialExcludingTax;
    }

    public void setSpecialExcludingTax(double specialExcludingTax) {
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

    public List<FishListOptions> getOptions() {
        return options;
    }

    public void setOptions(List<FishListOptions> options) {
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

    public Integer getStockStatusId() {
        return stockStatusId;
    }

    public void setStockStatusId(Integer stockStatusId) {
        this.stockStatusId = stockStatusId;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getTaxClassId() {
        return taxClassId;
    }

    public void setTaxClassId(Integer taxClassId) {
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

    public Integer getLengthClassId() {
        return lengthClassId;
    }

    public void setLengthClassId(Integer lengthClassId) {
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

    public Object getReward() {
        return reward;
    }

    public void setReward(Object reward) {
        this.reward = reward;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public List<FishListCategory> getCategory() {
        return category;
    }

    public void setCategory(List<FishListCategory> category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public FishListReview getReviews() {
        return reviews;
    }

    public void setReviews(FishListReview reviews) {
        this.reviews = reviews;
    }

    public List<Object> getRecurrings() {
        return recurrings;
    }

    public void setRecurrings(List<Object> recurrings) {
        this.recurrings = recurrings;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.needDetailedView ? (byte) 1 : (byte) 0);
        dest.writeByte(this.addToCartStatus ? (byte) 1 : (byte) 0);
        dest.writeValue(this.id);
        dest.writeValue(this.productId);
        dest.writeString(this.name);
        dest.writeString(this.sku);
        dest.writeString(this.model);
        dest.writeString(this.image);
        dest.writeStringList(this.images);
        dest.writeString(this.originalImage);
        dest.writeStringList(this.originalImages);
        dest.writeValue(this.priceExcludingTax);
        dest.writeString(this.priceExcludingTaxFormated);
        dest.writeValue(this.price);
        dest.writeString(this.priceFormated);
        dest.writeValue(this.rating);
        dest.writeString(this.description);
        dest.writeValue(this.special);
        dest.writeValue(this.specialExcludingTax);
        dest.writeString(this.specialExcludingTaxFormated);
        dest.writeString(this.specialFormated);
        dest.writeString(this.specialStartDate);
        dest.writeString(this.specialEndDate);
        dest.writeList(this.discounts);
        dest.writeList(this.options);
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
        dest.writeValue(this.stockStatusId);
        dest.writeValue(this.manufacturerId);
        dest.writeValue(this.taxClassId);
        dest.writeString(this.dateAvailable);
        dest.writeString(this.weight);
        dest.writeValue(this.weightClassId);
        dest.writeString(this.length);
        dest.writeString(this.width);
        dest.writeString(this.height);
        dest.writeValue(this.lengthClassId);
        dest.writeString(this.subtract);
        dest.writeString(this.sortOrder);
        dest.writeString(this.status);
        dest.writeString(this.dateAdded);
        dest.writeString(this.dateModified);
        dest.writeString(this.viewed);
        dest.writeString(this.weightClass);
        dest.writeString(this.lengthClass);
        dest.writeString(this.shipping);
        dest.writeString(this.points);
        dest.writeList(this.category);
        dest.writeValue(this.quantity);
        dest.writeInt(this.side);
    }

    public FishListdata() {
    }

    protected FishListdata(Parcel in) {
        this.needDetailedView = in.readByte() != 0;
        this.addToCartStatus = in.readByte() != 0;
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.productId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.sku = in.readString();
        this.model = in.readString();
        this.image = in.readString();
        this.images = in.createStringArrayList();
        this.originalImage = in.readString();
        this.originalImages = in.createStringArrayList();
        this.priceExcludingTax = (Integer) in.readValue(Integer.class.getClassLoader());
        this.priceExcludingTaxFormated = in.readString();
        this.price = (Integer) in.readValue(Integer.class.getClassLoader());
        this.priceFormated = in.readString();
        this.rating = (Integer) in.readValue(Integer.class.getClassLoader());
        this.description = in.readString();
        this.special = (Integer) in.readValue(Integer.class.getClassLoader());
        this.specialExcludingTax = (Integer) in.readValue(Integer.class.getClassLoader());
        this.specialExcludingTaxFormated = in.readString();
        this.specialFormated = in.readString();
        this.specialStartDate = in.readString();
        this.specialEndDate = in.readString();
        in.readList(this.discounts, Object.class.getClassLoader());
        this.options = new ArrayList<FishListOptions>();
        in.readList(this.options, FishListOptions.class.getClassLoader());
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
        this.stockStatusId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.manufacturerId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.taxClassId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dateAvailable = in.readString();
        this.weight = in.readString();
        this.weightClassId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.length = in.readString();
        this.width = in.readString();
        this.height = in.readString();
        this.lengthClassId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subtract = in.readString();
        this.sortOrder = in.readString();
        this.status = in.readString();
        this.dateAdded = in.readString();
        this.dateModified = in.readString();
        this.viewed = in.readString();
        this.weightClass = in.readString();
        this.lengthClass = in.readString();
        this.shipping = in.readString();
        this.points = in.readString();
        this.category = new ArrayList<FishListCategory>();
        in.readList(this.category, FishListCategory.class.getClassLoader());
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
        in.readList(this.recurrings, Object.class.getClassLoader());
        this.side = in.readInt();
    }

    public static final Parcelable.Creator<FishListdata> CREATOR = new Parcelable.Creator<FishListdata>() {
        @Override
        public FishListdata createFromParcel(Parcel source) {
            return new FishListdata(source);
        }

        @Override
        public FishListdata[] newArray(int size) {
            return new FishListdata[size];
        }
    };
}
