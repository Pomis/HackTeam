package kekify.io.hackteam;

import android.content.SharedPreferences;


public class PreferencesWrapper {
    private SharedPreferences sharedPreferences;

    public PreferencesWrapper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getAuthToken(String authType) {
        return sharedPreferences.getString(authType, "");
    }

    public void setAuthToken(String authType, String token) {
        sharedPreferences
                .edit()
                .putString(authType, token).apply();
    }

    public Integer getTwistId() {
        return sharedPreferences.getInt("twistId", 0);
    }
    public void setTwistId(Integer id) {
        sharedPreferences
                .edit()
                .putInt("twistId", id).apply();
    }

    public Integer getId() {
        return sharedPreferences.getInt("id", 0);
    }
    public void setId(Integer id) {
        sharedPreferences
                .edit()
                .putInt("id", id).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString("email", "");
    }
    public void setEmail(String email) {
        sharedPreferences
                .edit()
                .putString("email", email).apply();
    }

    public Integer getProjectId() {
        return sharedPreferences.getInt("projectId", 0);
    }
    public void setProjectId(int projectId) {
        sharedPreferences
                .edit()
                .putInt("projectId", projectId).apply();
    }

    public Integer getWorkspace() {
        return sharedPreferences.getInt("default_workspace", 0);
    }
    public void setWorkspace(int workspace) {
        sharedPreferences
                .edit()
                .putInt("default_workspace", workspace).apply();
    }

    public Integer getWorkspaceId() {
        return sharedPreferences.getInt("workspace_id", 0);
    }
    public void setWorkspaceId(int workspace) {
        sharedPreferences
                .edit()
                .putInt("workspace_id", workspace).apply();
    }
}
