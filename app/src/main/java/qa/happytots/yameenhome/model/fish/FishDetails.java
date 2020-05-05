package qa.happytots.yameenhome.model.fish;

import java.util.List;

import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.model.IFishListItem;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.FishListOptionvalue;

public class FishDetails implements IFishListItem, Bridge {
    private int fishId;
    private String offerPrice;
    private String ogPrice;
    private String landscapeUrl;
    private String name;
    private List<FishListOptionvalue> typeOptions;
    private int quantityOption = -1;
    private int mFinishKey;
    private int mFinishValue = -1;
    private boolean isAlreadyAddedToCart;
    private int parentPosition;

    public int getFishId() {
        return fishId;
    }

    public void setFishId(int fishId) {
        this.fishId = fishId;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOgPrice() {
        return ogPrice;
    }

    public void setOgPrice(String ogPrice) {
        this.ogPrice = ogPrice;
    }

    public List<FishListOptionvalue> getTypeOptions() {
        return typeOptions;
    }

    public void setTypeOptions(List<FishListOptionvalue> typeOptions) {
        this.typeOptions = typeOptions;
    }

    public String getLandscapeUrl() {
        return landscapeUrl;
    }

    public void setLandscapeUrl(String landscapeUrl) {
        this.landscapeUrl = landscapeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityOption() {
        return quantityOption;
    }

    public void setQuantityOption(int quantityOption) {
        this.quantityOption = quantityOption;
    }

    public int getmFinishKey() {
        return mFinishKey;
    }

    public void setmFinishKey(int mFinishKey) {
        this.mFinishKey = mFinishKey;
    }

    public int getmFinishValue() {
        return mFinishValue;
    }

    public void setmFinishValue(int mFinishValue) {
        this.mFinishValue = mFinishValue;
    }

    public boolean isAlreadyAddedToCart() {
        return isAlreadyAddedToCart;
    }

    public void setAlreadyAddedToCart(boolean alreadyAddedToCart) {
        isAlreadyAddedToCart = alreadyAddedToCart;
    }

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }
}
