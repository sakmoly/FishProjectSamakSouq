package qa.happytots.yameenhome.model.payment.methods.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import qa.happytots.yameenhome.view.adapter.Bridge;

public class Cod implements Bridge {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("terms")
    @Expose
    private String terms;
    @SerializedName("sort_order")
    @Expose
    private String sortOrder;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

}