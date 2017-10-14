package kekify.io.hackteam.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class User {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("project_id")
    @Expose
    private int project_id;

    @SerializedName("twist_id")
    @Expose
    private int twist_id;

    @SerializedName("twist_email")
    @Expose
    private String twist_email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("skills")
    @Expose
    private String skills;

    @SerializedName("roles")
    @Expose
    private List<String> roles;


    public User(String name, String skills, List<String> roles) {
        this.name = name;
        this.skills = skills;
        this.roles = roles;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }

    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getProject_id() {
        return project_id;
    }
    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getTwist_id() {
        return twist_id;
    }
    public void setTwist_id(int twist_id) {
        this.twist_id = twist_id;
    }

    public String getTwist_email() {
        return twist_email;
    }
    public void setTwist_email(String twist_email) {
        this.twist_email = twist_email;
    }
}
