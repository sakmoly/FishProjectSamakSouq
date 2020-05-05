package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class CartResponseProduct implements Parcelable, Bridge {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("option")
    @Expose
    private List<CartResponseproductOption> option = null;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("recurring")
    @Expose
    private String recurring;
    @SerializedName("stock")
    @Expose
    private Boolean stock;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("price_raw")
    @Expose
    private double priceRaw;
    @SerializedName("total_raw")
    @Expose
    private double totalRaw;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<CartResponseproductOption> getOption() {
        return option;
    }

    public void setOption(List<CartResponseproductOption> option) {
        this.option = option;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRecurring() {
        return recurring;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }

    public Boolean getStock() {
        return stock;
    }

    public void setStock(Boolean stock) {
        this.stock = stock;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public double getPriceRaw() {
        return priceRaw;
    }

    public void setPriceRaw(double priceRaw) {
        this.priceRaw = priceRaw;
    }

    public double getTotalRaw() {
        return totalRaw;
    }

    public void setTotalRaw(double totalRaw) {
        this.totalRaw = totalRaw;
    }

    public CartResponseProduct() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.thumb);
        dest.writeString(this.name);
        dest.writeValue(this.points);
        dest.writeString(this.productId);
        dest.writeString(this.model);
        dest.writeTypedList(this.option);
        dest.writeString(this.quantity);
        dest.writeString(this.recurring);
        dest.writeValue(this.stock);
        dest.writeString(this.reward);
        dest.writeString(this.price);
        dest.writeString(this.total);
        dest.writeDouble(this.priceRaw);
        dest.writeDouble(this.totalRaw);
    }

    protected CartResponseProduct(Parcel in) {
        this.key = in.readString();
        this.thumb = in.readString();
        this.name = in.readString();
        this.points = (Integer) in.readValue(Integer.class.getClassLoader());
        this.productId = in.readString();
        this.model = in.readString();
        this.option = in.createTypedArrayList(CartResponseproductOption.CREATOR);
        this.quantity = in.readString();
        this.recurring = in.readString();
        this.stock = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.reward = in.readString();
        this.price = in.readString();
        this.total = in.readString();
        this.priceRaw = in.readDouble();
        this.totalRaw = in.readDouble();
    }

    public static final Creator<CartResponseProduct> CREATOR = new Creator<CartResponseProduct>() {
        @Override
        public CartResponseProduct createFromParcel(Parcel source) {
            return new CartResponseProduct(source);
        }

        @Override
        public CartResponseProduct[] newArray(int size) {
            return new CartResponseProduct[size];
        }
    };
}
