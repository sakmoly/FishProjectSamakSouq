package qa.happytots.yameenhome.model.order;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class OrderSummary implements Bridge {

    private int orderId;
    private String date;
    private int slot;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
