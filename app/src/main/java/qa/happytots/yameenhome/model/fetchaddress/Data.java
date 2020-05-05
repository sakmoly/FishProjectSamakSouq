package qa.happytots.yameenhome.model.fetchaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

@SerializedName("address_id")
@Expose
private String addressId;
@SerializedName("addresses")
@Expose
private List<Address> addresses = null;

public String getAddressId() {
return addressId;
}

public void setAddressId(String addressId) {
this.addressId = addressId;
}

public List<Address> getAddresses() {
return addresses;
}

public void setAddresses(List<Address> addresses) {
this.addresses = addresses;
}

}