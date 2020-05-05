package qa.happytots.yameenhome.model.order_overview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("totals")
    @Expose
    private List<Total> totals = null;
    @SerializedName("invoice_prefix")
    @Expose
    private String invoicePrefix;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_url")
    @Expose
    private String storeUrl;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_group_id")
    @Expose
    private String customerGroupId;
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
    @SerializedName("payment_firstname")
    @Expose
    private String paymentFirstname;
    @SerializedName("payment_lastname")
    @Expose
    private String paymentLastname;
    @SerializedName("payment_company")
    @Expose
    private String paymentCompany;
    @SerializedName("payment_address_1")
    @Expose
    private String paymentAddress1;
    @SerializedName("payment_address_2")
    @Expose
    private String paymentAddress2;
    @SerializedName("payment_city")
    @Expose
    private String paymentCity;
    @SerializedName("payment_postcode")
    @Expose
    private String paymentPostcode;
    @SerializedName("payment_zone")
    @Expose
    private String paymentZone;
    @SerializedName("payment_zone_id")
    @Expose
    private String paymentZoneId;
    @SerializedName("payment_country")
    @Expose
    private String paymentCountry;
    @SerializedName("payment_country_id")
    @Expose
    private String paymentCountryId;
    @SerializedName("payment_address_format")
    @Expose
    private String paymentAddressFormat;
    @SerializedName("payment_custom_field")
    @Expose
    private Object paymentCustomField;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_code")
    @Expose
    private String paymentCode;
    @SerializedName("shipping_firstname")
    @Expose
    private String shippingFirstname;
    @SerializedName("shipping_lastname")
    @Expose
    private String shippingLastname;
    @SerializedName("shipping_company")
    @Expose
    private String shippingCompany;
    @SerializedName("shipping_address_1")
    @Expose
    private String shippingAddress1;
    @SerializedName("shipping_address_2")
    @Expose
    private String shippingAddress2;
    @SerializedName("shipping_city")
    @Expose
    private String shippingCity;
    @SerializedName("shipping_postcode")
    @Expose
    private String shippingPostcode;
    @SerializedName("shipping_zone")
    @Expose
    private String shippingZone;
    @SerializedName("shipping_zone_id")
    @Expose
    private String shippingZoneId;
    @SerializedName("shipping_country")
    @Expose
    private String shippingCountry;
    @SerializedName("shipping_country_id")
    @Expose
    private String shippingCountryId;
    @SerializedName("shipping_address_format")
    @Expose
    private String shippingAddressFormat;
    @SerializedName("shipping_custom_field")
    @Expose
    private List<Object> shippingCustomField = null;
    @SerializedName("shipping_method")
    @Expose
    private String shippingMethod;
    @SerializedName("shipping_code")
    @Expose
    private String shippingCode;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("vouchers")
    @Expose
    private List<Object> vouchers = null;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("affiliate_id")
    @Expose
    private Integer affiliateId;
    @SerializedName("commission")
    @Expose
    private Integer commission;
    @SerializedName("marketing_id")
    @Expose
    private Integer marketingId;
    @SerializedName("tracking")
    @Expose
    private String tracking;
    @SerializedName("language_id")
    @Expose
    private String languageId;
    @SerializedName("currency_id")
    @Expose
    private String currencyId;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("currency_value")
    @Expose
    private String currencyValue;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("forwarded_ip")
    @Expose
    private String forwardedIp;
    @SerializedName("user_agent")
    @Expose
    private String userAgent;
    @SerializedName("accept_language")
    @Expose
    private String acceptLanguage;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("payment")
    @Expose
    private String payment;

    public List<Total> getTotals() {
        return totals;
    }

    public void setTotals(List<Total> totals) {
        this.totals = totals;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

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


    public String getPaymentFirstname() {
        return paymentFirstname;
    }

    public void setPaymentFirstname(String paymentFirstname) {
        this.paymentFirstname = paymentFirstname;
    }

    public String getPaymentLastname() {
        return paymentLastname;
    }

    public void setPaymentLastname(String paymentLastname) {
        this.paymentLastname = paymentLastname;
    }

    public String getPaymentCompany() {
        return paymentCompany;
    }

    public void setPaymentCompany(String paymentCompany) {
        this.paymentCompany = paymentCompany;
    }

    public String getPaymentAddress1() {
        return paymentAddress1;
    }

    public void setPaymentAddress1(String paymentAddress1) {
        this.paymentAddress1 = paymentAddress1;
    }

    public String getPaymentAddress2() {
        return paymentAddress2;
    }

    public void setPaymentAddress2(String paymentAddress2) {
        this.paymentAddress2 = paymentAddress2;
    }

    public String getPaymentCity() {
        return paymentCity;
    }

    public void setPaymentCity(String paymentCity) {
        this.paymentCity = paymentCity;
    }

    public String getPaymentPostcode() {
        return paymentPostcode;
    }

    public void setPaymentPostcode(String paymentPostcode) {
        this.paymentPostcode = paymentPostcode;
    }

    public String getPaymentZone() {
        return paymentZone;
    }

    public void setPaymentZone(String paymentZone) {
        this.paymentZone = paymentZone;
    }

    public String getPaymentZoneId() {
        return paymentZoneId;
    }

    public void setPaymentZoneId(String paymentZoneId) {
        this.paymentZoneId = paymentZoneId;
    }

    public String getPaymentCountry() {
        return paymentCountry;
    }

    public void setPaymentCountry(String paymentCountry) {
        this.paymentCountry = paymentCountry;
    }

    public String getPaymentCountryId() {
        return paymentCountryId;
    }

    public void setPaymentCountryId(String paymentCountryId) {
        this.paymentCountryId = paymentCountryId;
    }

    public String getPaymentAddressFormat() {
        return paymentAddressFormat;
    }

    public void setPaymentAddressFormat(String paymentAddressFormat) {
        this.paymentAddressFormat = paymentAddressFormat;
    }

    public Object getPaymentCustomField() {
        return paymentCustomField;
    }

    public void setPaymentCustomField(Object paymentCustomField) {
        this.paymentCustomField = paymentCustomField;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getShippingFirstname() {
        return shippingFirstname;
    }

    public void setShippingFirstname(String shippingFirstname) {
        this.shippingFirstname = shippingFirstname;
    }

    public String getShippingLastname() {
        return shippingLastname;
    }

    public void setShippingLastname(String shippingLastname) {
        this.shippingLastname = shippingLastname;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public String getShippingAddress1() {
        return shippingAddress1;
    }

    public void setShippingAddress1(String shippingAddress1) {
        this.shippingAddress1 = shippingAddress1;
    }

    public String getShippingAddress2() {
        return shippingAddress2;
    }

    public void setShippingAddress2(String shippingAddress2) {
        this.shippingAddress2 = shippingAddress2;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingPostcode() {
        return shippingPostcode;
    }

    public void setShippingPostcode(String shippingPostcode) {
        this.shippingPostcode = shippingPostcode;
    }

    public String getShippingZone() {
        return shippingZone;
    }

    public void setShippingZone(String shippingZone) {
        this.shippingZone = shippingZone;
    }

    public String getShippingZoneId() {
        return shippingZoneId;
    }

    public void setShippingZoneId(String shippingZoneId) {
        this.shippingZoneId = shippingZoneId;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public String getShippingCountryId() {
        return shippingCountryId;
    }

    public void setShippingCountryId(String shippingCountryId) {
        this.shippingCountryId = shippingCountryId;
    }

    public String getShippingAddressFormat() {
        return shippingAddressFormat;
    }

    public void setShippingAddressFormat(String shippingAddressFormat) {
        this.shippingAddressFormat = shippingAddressFormat;
    }

    public List<Object> getShippingCustomField() {
        return shippingCustomField;
    }

    public void setShippingCustomField(List<Object> shippingCustomField) {
        this.shippingCustomField = shippingCustomField;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Object> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Object> vouchers) {
        this.vouchers = vouchers;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(Integer affiliateId) {
        this.affiliateId = affiliateId;
    }

    public Integer getCommission() {
        return commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public Integer getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(Integer marketingId) {
        this.marketingId = marketingId;
    }

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(String currencyValue) {
        this.currencyValue = currencyValue;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getForwardedIp() {
        return forwardedIp;
    }

    public void setForwardedIp(String forwardedIp) {
        this.forwardedIp = forwardedIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

}