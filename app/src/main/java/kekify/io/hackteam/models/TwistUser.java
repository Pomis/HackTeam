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
}
