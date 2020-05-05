package qa.happytots.yameenhome.retrofit.Retrofit_Models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomField {

    @SerializedName("custom_field_id")
    @Expose
    private String customFieldId;
    @SerializedName("custom_field_value")
    @Expose
    private List<CustomFieldValue> customFieldValue = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("validation")
    @Expose
    private String validation;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("required")
    @Expose
    private Boolean required;
    @SerializedName("sort_order")
    @Expose
    private String sortOrder;

    public String getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(String customFieldId) {
        this.customFieldId = customFieldId;
    }

    public List<CustomFieldValue> getCustomFieldValue() {
        return customFieldValue;
    }

    public void setCustomFieldValue(List<CustomFieldValue> customFieldValue) {
        this.customFieldValue = customFieldValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}