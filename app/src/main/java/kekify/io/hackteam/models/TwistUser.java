package kekify.io.hackteam.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TwistUser {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("default_workspace")
    @Expose
    private int default_workspace;

    public TwistUser(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getDefault_workspace() {
        return default_workspace;
    }
    public void setDefault_workspace(int default_workspace) {
        this.default_workspace = default_workspace;
    }
}
