package qa.happytots.yameenhome.model.coupon;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class CouponHeader implements Bridge {
    private boolean isExpanded;
    private String header;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
