package qa.happytots.yameenhome.model.register.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerGroup {

    @SerializedName("customer_group_id")
    @Expose
    private String customerGroupId;
    @SerializedName("approval")
    @Expose
    private String approval;
    @SerializedName("sort_order")
    @Expose
    private String sortOrder;
    @SerializedName("language_id")
    @Expose
    private String languageId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    public String getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}