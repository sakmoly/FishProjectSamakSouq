package qa.happytots.yameenhome.model.order_overview;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class ShipmentAddress implements Bridge {
    private String name;
    private String address1;
    private String address2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }
}
