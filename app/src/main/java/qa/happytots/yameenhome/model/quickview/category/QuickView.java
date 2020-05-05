package qa.happytots.yameenhome.model.quickview.category;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class QuickView implements Bridge, Parcelable {

    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("seo_url")
    @Expose
    private String seoUrl;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("original_image")
    @Expose
    private String originalImage;
    @SerializedName("categories")
    @Expose
    private List<Object> categories = null;

    private boolean selected;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeoUrl() {
        return seoUrl;
    }

    public void setSeoUrl(String seoUrl) {
        this.seoUrl = seoUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public QuickView() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.categoryId);
        dest.writeValue(this.parentId);
        dest.writeString(this.name);
        dest.writeString(this.seoUrl);
        dest.writeString(this.image);
        dest.writeString(this.originalImage);
        dest.writeList(this.categories);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    protected QuickView(Parcel in) {
        this.categoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.parentId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.seoUrl = in.readString();
        this.image = in.readString();
        this.originalImage = in.readString();
        this.categories = new ArrayList<Object>();
        in.readList(this.categories, Object.class.getClassLoader());
        this.selected = in.readByte() != 0;
    }

    public static final Creator<QuickView> CREATOR = new Creator<QuickView>() {
        @Override
        public QuickView createFromParcel(Parcel source) {
            return new QuickView(source);
        }

        @Override
        public QuickView[] newArray(int size) {
            return new QuickView[size];
        }
    };
}