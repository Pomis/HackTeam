package kekify.io.hackteam.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AccessTokenRequest {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("scope")
    @Expose
    private List<String> scope = null;
    @SerializedName("client_id")
    @Expose
    private String clientId;

    public AccessTokenRequest(String code, List<String> scope, String clientId) {
        this.code = code;
        this.scope = scope;
        this.clientId = clientId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
