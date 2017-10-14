package kekify.io.hackteam.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AccessTokenResponse {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String token_type;

    public AccessTokenResponse(String accessToken, String token_type) {
        this.accessToken = accessToken;
        this.token_type = token_type;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getToken_type() {
        return token_type;
    }
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}

