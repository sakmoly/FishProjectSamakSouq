package qa.happytots.yameenhome.model.order_overview;

import qa.happytots.yameenhome.retrofit.Retrofit_Models.CartResponseTotal;
import qa.happytots.yameenhome.view.adapter.Bridge;

public class OrderTotal implements Bridge {
    private CartResponseTotal mSubTotal;
    private CartResponseTotal mCoupon;
    private CartResponseTotal mShipmentTotal;
    private CartResponseTotal mGrandTotal;
    private boolean isDeliveryFree;
    private boolean isCOD;

    public CartResponseTotal getSubTotal() {
        return mSubTotal;
    }

    public void setSubTotal(CartResponseTotal mSubTotal) {
        this.mSubTotal = mSubTotal;
    }

    public CartResponseTotal getShipmentTotal() {
        return mShipmentTotal;
    }

    public void setShipmentTotal(CartResponseTotal mShipmentTotal) {
        this.mShipmentTotal = mShipmentTotal;
    }

    public CartResponseTotal getCoupon() {
        return mCoupon;
    }

    public void setCoupon(CartResponseTotal mCoupon) {
        this.mCoupon = mCoupon;
    }

    public CartResponseTotal getGrandTotal() {
        return mGrandTotal;
    }

    public void setGrandTotal(CartResponseTotal mGrandTotal) {
        this.mGrandTotal = mGrandTotal;
    }

    public boolean isDeliveryFree() {
        return isDeliveryFree;
    }

    public void setDeliveryFree(boolean deliveryFree) {
        isDeliveryFree = deliveryFree;
    }

    public boolean isCOD() {
        return isCOD;
    }

    public void setCOD(boolean COD) {
        this.isCOD = COD;
    }
}
