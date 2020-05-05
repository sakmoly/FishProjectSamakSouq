package qa.happytots.yameenhome.model;

import qa.happytots.yameenhome.model.order_overview.Total;
import qa.happytots.yameenhome.view.adapter.Bridge;

public class OrderSuccessTotal implements Bridge {

    private Total mSubTotal;
    private Total mCoupon;
    private Total mShipmentTotal;
    private Total mGrandTotal;
    private boolean isDeliveryFree;
    private boolean isCOD;


    public Total getmSubTotal() {
        return mSubTotal;
    }

    public void setmSubTotal(Total mSubTotal) {
        this.mSubTotal = mSubTotal;
    }

    public Total getmCoupon() {
        return mCoupon;
    }

    public void setmCoupon(Total mCoupon) {
        this.mCoupon = mCoupon;
    }

    public Total getmShipmentTotal() {
        return mShipmentTotal;
    }

    public void setmShipmentTotal(Total mShipmentTotal) {
        this.mShipmentTotal = mShipmentTotal;
    }

    public Total getmGrandTotal() {
        return mGrandTotal;
    }

    public void setmGrandTotal(Total mGrandTotal) {
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
        isCOD = COD;
    }
}
