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

    public String getDeviceId(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setDeviceId(String key, String id) {
        sharedPreferences
                .edit()
                .putString(key, id).apply();
    }
}
