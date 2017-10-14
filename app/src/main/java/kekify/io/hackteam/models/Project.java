package kekify.io.hackteam.models;

import java.util.List;


public class Project {

    private String description;
    private List<String> roles;


    public Project(String description, List<String> roles) {
        this.description = description;
        this.roles = roles;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
