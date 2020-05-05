package qa.happytots.yameenhome.model;

import android.os.Parcel;
import android.os.Parcelable;

import qa.happytots.yameenhome.view.adapter.Bridge;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseTotal;

public class CartTotal implements Bridge, Parcelable {

    private CartResponseTotal subTotal;
    private CartResponseTotal coupon;
    private CartResponseTotal total;
    private CartResponseTotal delivery;
    private boolean isDeliveryFree;

    public CartResponseTotal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(CartResponseTotal subTotal) {
        this.subTotal = subTotal;
    }

    public CartResponseTotal getCoupon() {
        return coupon;
    }

    public void setCoupon(CartResponseTotal coupon) {
        this.coupon = coupon;
    }

    public CartResponseTotal getTotal() {
        return total;
    }

    public void setTotal(CartResponseTotal total) {
        this.total = total;
    }

    public boolean isDeliveryFree() {
        return isDeliveryFree;
    }

    public void setDeliveryFree(boolean deliveryFree) {
        isDeliveryFree = deliveryFree;
    }

    public CartResponseTotal getDelivery() {
        return delivery;
    }

    public void setDelivery(CartResponseTotal delivery) {
        this.delivery = delivery;
    }

    public CartTotal() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.subTotal, flags);
        dest.writeParcelable(this.coupon, flags);
        dest.writeParcelable(this.total, flags);
        dest.writeByte(this.isDeliveryFree ? (byte) 1 : (byte) 0);
    }

    protected CartTotal(Parcel in) {
        this.subTotal = in.readParcelable(CartResponseTotal.class.getClassLoader());
        this.coupon = in.readParcelable(CartResponseTotal.class.getClassLoader());
        this.total = in.readParcelable(CartResponseTotal.class.getClassLoader());
        this.isDeliveryFree = in.readByte() != 0;
    }

    public static final Creator<CartTotal> CREATOR = new Creator<CartTotal>() {
        @Override
        public CartTotal createFromParcel(Parcel source) {
            return new CartTotal(source);
        }

        @Override
        public CartTotal[] newArray(int size) {
            return new CartTotal[size];
        }
    };
}
