package qa.happytots.yameenhome.model.register.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomField {

@SerializedName("account")
@Expose
private Account account;

public Account getAccount() {
return account;
}

public void setAccount(Account account) {
this.account = account;
}

}