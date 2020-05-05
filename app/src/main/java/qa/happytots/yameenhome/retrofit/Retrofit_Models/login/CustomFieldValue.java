package qa.happytots.yameenhome.retrofit.Retrofit_Models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomFieldValue {

    @SerializedName("custom_field_value_id")
    @Expose
    private String customFieldValueId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getCustomFieldValueId() {
        return customFieldValueId;
    }

    public void setCustomFieldValueId(String customFieldValueId) {
        this.customFieldValueId = customFieldValueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}