package com.project.tourismapp.DataClasses;

import android.content.Context;
import android.content.SharedPreferences;

import com.project.tourismapp.R;

import java.io.Serializable;

public class Session implements Serializable {

    private User user;

    public boolean login(Context context, String email, String password, boolean saveSession) {
        this.user = new User(email);

        if (saveSession) {
            storeSession(context, email, password);
        }

        return true;
    }

    public boolean authenticate() {
        //TODO Check login credentials here
        return true;
    }

    public void logout(Context context) {
        storeSession(context, "", "");
    }

    public boolean restoreSession(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String email = sharedPref.getString(context.getString(R.string.email_key), "");
        if (!email.isEmpty()) {
            String password = sharedPref.getString(context.getString(R.string.password_key), "");
            return login(context, email, password, false);
        } else {
            return false;
        }
    }

    private void storeSession(Context context, String email, String password) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor loginEditor = sharedPref.edit();
        loginEditor.putString(context.getString(R.string.email_key), email);
        loginEditor.putString(context.getString(R.string.password_key), password);
        loginEditor.apply();
    }

    public User getUser() {
        return user;
    }
}