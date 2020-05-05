package qa.happytots.yameenhome.model.order_overview;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class OrderDetailTotal implements Bridge {
    private OrderDetailTotalOg SubTotal;
    private OrderDetailTotalOg Coupon;
    private OrderDetailTotalOg ShipmentTotal;
    private OrderDetailTotalOg GrandTotal;
    private boolean isDeliveryFree;
    private boolean isCOD;

    public OrderDetailTotalOg getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(OrderDetailTotalOg subTotal) {
        SubTotal = subTotal;
    }

    public OrderDetailTotalOg getCoupon() {
        return Coupon;
    }

    public void setCoupon(OrderDetailTotalOg coupon) {
        Coupon = coupon;
    }

    public OrderDetailTotalOg getShipmentTotal() {
        return ShipmentTotal;
    }

    public void setShipmentTotal(OrderDetailTotalOg shipmentTotal) {
        ShipmentTotal = shipmentTotal;
    }

    public OrderDetailTotalOg getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(OrderDetailTotalOg grandTotal) {
        GrandTotal = grandTotal;
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
