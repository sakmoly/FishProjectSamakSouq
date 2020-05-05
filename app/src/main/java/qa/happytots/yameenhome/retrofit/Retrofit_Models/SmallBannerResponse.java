package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.model.IFishListItem;

public class SmallBannerResponse implements IFishListItem {


    public SmallBannerResponse() {

    }

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("error")
    @Expose
    private List<Object> error = null;
    @SerializedName("data")
    @Expose
    private ArrayList<SmallBannerData> data = null;

    private int mCurrentPosition;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Object> getError() {
        return error;
    }

    public void setError(List<Object> error) {
        this.error = error;
    }

    public ArrayList<SmallBannerData> getData() {
        return data;
    }

    public void setData(ArrayList<SmallBannerData> data) {
        this.data = data;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
    }
}
