package qa.happytots.yameenhome.retrofit.Retrofit_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import qa.happytots.yameenhome.model.register.response.CustomField;
import qa.happytots.yameenhome.retrofit.Retrofit_Models.login.AccountCustomField;

public class LoginResponseData {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_group_id")
    @Expose
    private String customerGroupId;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("language_id")
    @Expose
    private String languageId;
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
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("cart")
    @Expose
    private Object cart;
    @SerializedName("wishlist")
    @Expose
    private Object wishlist;
    @SerializedName("newsletter")
    @Expose
    private String newsletter;
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("safe")
    @Expose
    private String safe;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("custom_fields")
    @Expose
    private List<CustomField> customFields = null;
    @SerializedName("account_custom_field")
    @Expose
    private AccountCustomField accountCustomField;
    @SerializedName("reward_total")
    @Expose
    private String rewardTotal;
    @SerializedName("user_balance")
    @Expose
    private String userBalance;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Object getCart() {
        return cart;
    }

    public void setCart(Object cart) {
        this.cart = cart;
    }

    public Object getWishlist() {
        return wishlist;
    }

    public void setWishlist(Object wishlist) {
        this.wishlist = wishlist;
    }

    public String getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(String newsletter) {
        this.newsletter = newsletter;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSafe() {
        return safe;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }

    public AccountCustomField getAccountCustomField() {
        return accountCustomField;
    }

    public void setAccountCustomField(AccountCustomField accountCustomField) {
        this.accountCustomField = accountCustomField;
    }

    public String getRewardTotal() {
        return rewardTotal;
    }

    public void setRewardTotal(String rewardTotal) {
        this.rewardTotal = rewardTotal;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }

}
