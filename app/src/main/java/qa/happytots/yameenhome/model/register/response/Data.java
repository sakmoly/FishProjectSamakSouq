package qa.happytots.yameenhome.model.register.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

@SerializedName("firstname")
@Expose
private String firstname;
@SerializedName("lastname")
@Expose
private String lastname;
@SerializedName("email")
@Expose
private String email;
@SerializedName("telephone")
@Expose
private String telephone;
@SerializedName("customer_group_id")
@Expose
private String customerGroupId;
@SerializedName("customer_groups")
@Expose
private List<CustomerGroup> customerGroups = null;
@SerializedName("company")
@Expose
private String company;
@SerializedName("customer_otp")
@Expose
private String customerOTP;
@SerializedName("customer_id")
@Expose
private Integer customerId;
@SerializedName("address_id")
@Expose
private String addressId;

public String getFirstname() {
return firstname;
}

public void setFirstname(String firstname) {
this.firstname = firstname;
}

public String getLastname() {
return lastname;
}

public void setLastname(String lastname) {
this.lastname = lastname;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getTelephone() {
return telephone;
}

public void setTelephone(String telephone) {
this.telephone = telephone;
}

public String getCustomerGroupId() {
return customerGroupId;
}

public void setCustomerGroupId(String customerGroupId) {
this.customerGroupId = customerGroupId;
}

public List<CustomerGroup> getCustomerGroups() {
return customerGroups;
}

public void setCustomerGroups(List<CustomerGroup> customerGroups) {
this.customerGroups = customerGroups;
}

public String getCompany() {
return company;
}

public void setCompany(String company) {
this.company = company;
}

public Integer getCustomerId() {
return customerId;
}

public void setCustomerId(Integer customerId) {
this.customerId = customerId;
}

public String getAddressId() {
return addressId;
}

public void setAddressId(String addressId) {
this.addressId = addressId;
}

    public String getCustomerOTP() {
        return customerOTP;
    }

    public void setCustomerOTP(String customerOTP) {
        this.customerOTP = customerOTP;
    }
}