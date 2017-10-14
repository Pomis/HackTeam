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
}
