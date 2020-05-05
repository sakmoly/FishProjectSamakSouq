package qa.happytots.yameenhome.model.order.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import qa.happytots.yameenhome.model.order_overview.OrderDetailTotalOg;

public class OrderDetail {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("invoice_prefix")
    @Expose
    private String invoicePrefix;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_url")
    @Expose
    private String storeUrl;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("email")
    @Expose
    private String email;
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
    @SerializedName("payment_postcode")
    @Expose
    private String paymentPostcode;
    @SerializedName("payment_city")
    @Expose
    private String paymentCity;
    @SerializedName("payment_zone_id")
    @Expose
    private String paymentZoneId;
    @SerializedName("payment_zone")
    @Expose
    private String paymentZone;
    @SerializedName("payment_zone_code")
    @Expose
    private String paymentZoneCode;
    @SerializedName("payment_country_id")
    @Expose
    private String paymentCountryId;
    @SerializedName("payment_country")
    @Expose
    private String paymentCountry;
    @SerializedName("payment_iso_code_2")
    @Expose
    private String paymentIsoCode2;
    @SerializedName("payment_iso_code_3")
    @Expose
    private String paymentIsoCode3;
    @SerializedName("payment_address_format")
    @Expose
    private String paymentAddressFormat;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
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
    @SerializedName("shipping_postcode")
    @Expose
    private String shippingPostcode;
    @SerializedName("shipping_city")
    @Expose
    private String shippingCity;
    @SerializedName("shipping_zone_id")
    @Expose
    private String shippingZoneId;
    @SerializedName("shipping_zone")
    @Expose
    private String shippingZone;
    @SerializedName("shipping_zone_code")
    @Expose
    private String shippingZoneCode;
    @SerializedName("shipping_country_id")
    @Expose
    private String shippingCountryId;
    @SerializedName("shipping_country")
    @Expose
    private String shippingCountry;
    @SerializedName("shipping_iso_code_2")
    @Expose
    private String shippingIsoCode2;
    @SerializedName("shipping_iso_code_3")
    @Expose
    private String shippingIsoCode3;
    @SerializedName("shipping_address_format")
    @Expose
    private String shippingAddressFormat;
    @SerializedName("shipping_method")
    @Expose
    private String shippingMethod;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("order_status_id")
    @Expose
    private String orderStatusId;
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
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("payment_address")
    @Expose
    private String paymentAddress;
    @SerializedName("shipping_address")
    @Expose
    private String shippingAddress;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("vouchers")
    @Expose
    private List<Object> vouchers = null;
    @SerializedName("totals")
    @Expose
    private List<OrderDetailTotalOg> totals = null;
    @SerializedName("histories")
    @Expose
    private List<History> histories = null;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("currency")
    @Expose
    private Currency currency;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPaymentPostcode() {
        return paymentPostcode;
    }

    public void setPaymentPostcode(String paymentPostcode) {
        this.paymentPostcode = paymentPostcode;
    }

    public String getPaymentCity() {
        return paymentCity;
    }

    public void setPaymentCity(String paymentCity) {
        this.paymentCity = paymentCity;
    }

    public String getPaymentZoneId() {
        return paymentZoneId;
    }

    public void setPaymentZoneId(String paymentZoneId) {
        this.paymentZoneId = paymentZoneId;
    }

    public String getPaymentZone() {
        return paymentZone;
    }

    public void setPaymentZone(String paymentZone) {
        this.paymentZone = paymentZone;
    }

    public String getPaymentZoneCode() {
        return paymentZoneCode;
    }

    public void setPaymentZoneCode(String paymentZoneCode) {
        this.paymentZoneCode = paymentZoneCode;
    }

    public String getPaymentCountryId() {
        return paymentCountryId;
    }

    public void setPaymentCountryId(String paymentCountryId) {
        this.paymentCountryId = paymentCountryId;
    }

    public String getPaymentCountry() {
        return paymentCountry;
    }

    public void setPaymentCountry(String paymentCountry) {
        this.paymentCountry = paymentCountry;
    }

    public String getPaymentIsoCode2() {
        return paymentIsoCode2;
    }

    public void setPaymentIsoCode2(String paymentIsoCode2) {
        this.paymentIsoCode2 = paymentIsoCode2;
    }

    public String getPaymentIsoCode3() {
        return paymentIsoCode3;
    }

    public void setPaymentIsoCode3(String paymentIsoCode3) {
        this.paymentIsoCode3 = paymentIsoCode3;
    }

    public String getPaymentAddressFormat() {
        return paymentAddressFormat;
    }

    public void setPaymentAddressFormat(String paymentAddressFormat) {
        this.paymentAddressFormat = paymentAddressFormat;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public String getShippingPostcode() {
        return shippingPostcode;
    }

    public void setShippingPostcode(String shippingPostcode) {
        this.shippingPostcode = shippingPostcode;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingZoneId() {
        return shippingZoneId;
    }

    public void setShippingZoneId(String shippingZoneId) {
        this.shippingZoneId = shippingZoneId;
    }

    public String getShippingZone() {
        return shippingZone;
    }

    public void setShippingZone(String shippingZone) {
        this.shippingZone = shippingZone;
    }

    public String getShippingZoneCode() {
        return shippingZoneCode;
    }

    public void setShippingZoneCode(String shippingZoneCode) {
        this.shippingZoneCode = shippingZoneCode;
    }

    public String getShippingCountryId() {
        return shippingCountryId;
    }

    public void setShippingCountryId(String shippingCountryId) {
        this.shippingCountryId = shippingCountryId;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public String getShippingIsoCode2() {
        return shippingIsoCode2;
    }

    public void setShippingIsoCode2(String shippingIsoCode2) {
        this.shippingIsoCode2 = shippingIsoCode2;
    }

    public String getShippingIsoCode3() {
        return shippingIsoCode3;
    }

    public void setShippingIsoCode3(String shippingIsoCode3) {
        this.shippingIsoCode3 = shippingIsoCode3;
    }

    public String getShippingAddressFormat() {
        return shippingAddressFormat;
    }

    public void setShippingAddressFormat(String shippingAddressFormat) {
        this.shippingAddressFormat = shippingAddressFormat;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(String orderStatusId) {
        this.orderStatusId = orderStatusId;
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

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(String paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
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

    public List<OrderDetailTotalOg> getTotals() {
        return totals;
    }

    public void setTotals(List<OrderDetailTotalOg> totals) {
        this.totals = totals;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}