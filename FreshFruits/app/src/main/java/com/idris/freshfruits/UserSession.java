package com.idris.freshfruits;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    private final String USERNAME = "Username";
    private final String IS_LOGIN = "LoggedIn";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public UserSession(Context context) {
        String PREF_NAME = "USER_SESSION";
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void createLoginSession(String username) {
        editor.putString(USERNAME, username);
        editor.putBoolean(IS_LOGIN, true);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME, "");
    }
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
