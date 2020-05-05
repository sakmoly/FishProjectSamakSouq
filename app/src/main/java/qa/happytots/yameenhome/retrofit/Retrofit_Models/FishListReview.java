package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FishListReview implements Parcelable {

    @SerializedName("review_total")
    @Expose
    private String reviewTotal;

    public String getReviewTotal() {
        return reviewTotal;
    }

    public void setReviewTotal(String reviewTotal) {
        this.reviewTotal = reviewTotal;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reviewTotal);
    }

    public FishListReview() {
    }

    protected FishListReview(Parcel in) {
        this.reviewTotal = in.readString();
    }

    public static final Parcelable.Creator<FishListReview> CREATOR = new Parcelable.Creator<FishListReview>() {
        @Override
        public FishListReview createFromParcel(Parcel source) {
            return new FishListReview(source);
        }

        @Override
        public FishListReview[] newArray(int size) {
            return new FishListReview[size];
        }
    };
}
