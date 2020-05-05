package qa.happytots.yameenhome.model.fetchaddress;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class Address implements Parcelable, Bridge {

    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("address_1")
    @Expose
    private String address1;
    @SerializedName("address_2")
    @Expose
    private String address2;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zone_id")
    @Expose
    private String zoneId;
    @SerializedName("zone")
    @Expose
    private String zone;
    @SerializedName("zone_code")
    @Expose
    private String zoneCode;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("iso_code_2")
    @Expose
    private String isoCode2;
    @SerializedName("iso_code_3")
    @Expose
    private String isoCode3;
    @SerializedName("address_format")
    @Expose
    private String addressFormat;
//    @SerializedName("custom_field")
//    @Expose
//    private Object customField;

    private boolean selected;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsoCode2() {
        return isoCode2;
    }

    public void setIsoCode2(String isoCode2) {
        this.isoCode2 = isoCode2;
    }

    public String getIsoCode3() {
        return isoCode3;
    }

    public void setIsoCode3(String isoCode3) {
        this.isoCode3 = isoCode3;
    }

    public String getAddressFormat() {
        return addressFormat;
    }

    public void setAddressFormat(String addressFormat) {
        this.addressFormat = addressFormat;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    //    public Object getCustomField() {
//        return customField;
//    }
//
//    public void setCustomField(Object customField) {
//        this.customField = customField;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.addressId);
        dest.writeString(this.firstname);
        dest.writeString(this.lastname);
        dest.writeString(this.company);
        dest.writeString(this.address1);
        dest.writeString(this.address2);
        dest.writeString(this.postcode);
        dest.writeString(this.city);
        dest.writeString(this.zoneId);
        dest.writeString(this.zone);
        dest.writeString(this.zoneCode);
        dest.writeString(this.countryId);
        dest.writeString(this.country);
        dest.writeString(this.isoCode2);
        dest.writeString(this.isoCode3);
        dest.writeString(this.addressFormat);
//        dest.writeParcelable(this.customField, flags);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.addressId = in.readString();
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.company = in.readString();
        this.address1 = in.readString();
        this.address2 = in.readString();
        this.postcode = in.readString();
        this.city = in.readString();
        this.zoneId = in.readString();
        this.zone = in.readString();
        this.zoneCode = in.readString();
        this.countryId = in.readString();
        this.country = in.readString();
        this.isoCode2 = in.readString();
        this.isoCode3 = in.readString();
        this.addressFormat = in.readString();
//        this.customField = in.readParcelable(Object.class.getClassLoader());
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}