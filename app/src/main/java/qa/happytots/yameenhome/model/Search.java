package qa.happytots.yameenhome.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Search implements IFishListItem, Parcelable {

    public int FINISH_SMALL;
    public int FINISH_MEDIUM;
    public int FINISH_LARGE;
    public int FINISH_SHELL;

    public int todaysOfferId = -1;

    private int min;
    private int max;
    private int selected;
    private boolean isSize;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public boolean isSize() {
        return isSize;
    }

    public void setSize(boolean size) {
        isSize = size;
    }

    public int getFINISHSMALL() {
        return FINISH_SMALL;
    }

    public void setFINISHSMALL(int FINISH_SMALL) {
        this.FINISH_SMALL = FINISH_SMALL;
    }

    public int getFINISHMEDIUM() {
        return FINISH_MEDIUM;
    }

    public void setFINISHMEDIUM(int FINISH_MEDIUM) {
        this.FINISH_MEDIUM = FINISH_MEDIUM;
    }

    public int getFINISHLARGE() {
        return FINISH_LARGE;
    }

    public void setFINISHLARGE(int FINISH_LARGE) {
        this.FINISH_LARGE = FINISH_LARGE;
    }

    public int getFINISHSHELL() {
        return FINISH_SHELL;
    }

    public void setFINISHSHELL(int FINISH_SHELL) {
        this.FINISH_SHELL = FINISH_SHELL;
    }

    public int getTodaysOfferId() {
        return todaysOfferId;
    }

    public void setTodaysOfferId(int todaysOfferId) {
        this.todaysOfferId = todaysOfferId;
    }


    public Search() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.FINISH_SMALL);
        dest.writeInt(this.FINISH_MEDIUM);
        dest.writeInt(this.FINISH_LARGE);
        dest.writeInt(this.FINISH_SHELL);
        dest.writeInt(this.todaysOfferId);
        dest.writeInt(this.min);
        dest.writeInt(this.max);
        dest.writeInt(this.selected);
        dest.writeByte(this.isSize ? (byte) 1 : (byte) 0);
    }

    protected Search(Parcel in) {
        this.FINISH_SMALL = in.readInt();
        this.FINISH_MEDIUM = in.readInt();
        this.FINISH_LARGE = in.readInt();
        this.FINISH_SHELL = in.readInt();
        this.todaysOfferId = in.readInt();
        this.min = in.readInt();
        this.max = in.readInt();
        this.selected = in.readInt();
        this.isSize = in.readByte() != 0;
    }

    public static final Creator<Search> CREATOR = new Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel source) {
            return new Search(source);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
}
