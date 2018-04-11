package com.oneclass.giphy.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chrisszendrovits on 2018-04-09.
 */

public class PersistLogin {
    private SharedPreferences sharedPreferences;
    private final String preferenceFileKey = "login_credentials";
    private final String usernameKey = "sp_username";
    private final String passwordKey = "sp_password";

    public PersistLogin(Context context, int mode) {
        this.sharedPreferences = context.getSharedPreferences(preferenceFileKey, mode);
    }

    public void saveLoginCredentials(LoginCredentials credentials) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.usernameKey, credentials.username);
        editor.putString(this.passwordKey, credentials.password);
        editor.commit();
    }

    public LoginCredentials restoreLoginCredentials() {
        String username = sharedPreferences.getString(this.usernameKey, null);
        String password = sharedPreferences.getString(this.passwordKey, null);

        if (username == null || password == null) {
            return null;
        }
        else {
            LoginCredentials credentials = new LoginCredentials(username, password);
            return credentials;
        }
    }

    static public class LoginCredentials {
        public String username;
        public String password;

        public LoginCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
