package qa.happytots.yameenhome.model.delivery;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class PaymentOption implements Bridge {
    String option;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
