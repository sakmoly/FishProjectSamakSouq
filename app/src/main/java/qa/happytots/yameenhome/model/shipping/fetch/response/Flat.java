package qa.happytots.yameenhome.model.shipping.fetch.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flat {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("quote")
    @Expose
    private Quote quote;
    @SerializedName("sort_order")
    @Expose
    private String sortOrder;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

}