package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Add_to_cart_raw {

    private String product_id;
    private String quantity;
    private Add_to_cart_raw_options option;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Add_to_cart_raw_options getOption() {
        return option;
    }

    public void setOption(Add_to_cart_raw_options option) {
        this.option = option;
    }


}
