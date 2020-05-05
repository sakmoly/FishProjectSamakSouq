package qa.happytots.yameenhome.model.coupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponRequest {

@SerializedName("coupon")
@Expose
private String coupon;

public String getCoupon() {
return coupon;
}

public void setCoupon(String coupon) {
this.coupon = coupon;
}

}