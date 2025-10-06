package ru.mirea.zakirovakr.data.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesStorage {
    private static final String PREFS_NAME = "user_preferences";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_LAST_LOGIN = "last_login";
    private static final String KEY_THEME = "app_theme";

    private final SharedPreferences sharedPreferences;

    public PreferencesStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserEmail(String email) {
        sharedPreferences.edit().putString(KEY_USER_EMAIL, email).apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, "");
    }

    public void saveUserName(String name) {
        sharedPreferences.edit().putString(KEY_USER_NAME, name).apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, "Гость");
    }

    public void saveLastLogin(long timestamp) {
        sharedPreferences.edit().putLong(KEY_LAST_LOGIN, timestamp).apply();
    }

    public long getLastLogin() {
        return sharedPreferences.getLong(KEY_LAST_LOGIN, 0);
    }

    public void saveTheme(String theme) {
        sharedPreferences.edit().putString(KEY_THEME, theme).apply();
    }

    public String getTheme() {
        return sharedPreferences.getString(KEY_THEME, "light");
    }

    public void savePreference(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getPreference(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void clearUserData() {
        sharedPreferences.edit()
                .remove(KEY_USER_EMAIL)
                .remove(KEY_USER_NAME)
                .remove(KEY_LAST_LOGIN)
                .apply();
    }
}