package qa.happytots.yameenhome.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialLoginRequest {

@SerializedName("email")
@Expose
private String email;
@SerializedName("access_token")
@Expose
private String accessToken;
@SerializedName("provider")
@Expose
private String provider;

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getAccessToken() {
return accessToken;
}

public void setAccessToken(String accessToken) {
this.accessToken = accessToken;
}

public String getProvider() {
return provider;
}

public void setProvider(String provider) {
this.provider = provider;
}

}