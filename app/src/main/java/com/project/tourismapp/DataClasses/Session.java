package com.project.tourismapp.DataClasses;

import android.content.Context;
import android.content.SharedPreferences;

import com.project.tourismapp.Helpers.JSONHelper;
import com.project.tourismapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Session implements Serializable {

    private User user;
    private transient JSONArray userObjects; //Exclude from serialization

    public boolean login(Context context, String email, String password, boolean saveSession) {
        this.user = new User(email);

        try {
            for (int i = 0; i < this.userObjects.length(); i++) {
                JSONObject user = userObjects.getJSONObject(i);
                if (user.getString(context.getString(R.string.username_json_key)).equals(email) &&
                        user.getString(context.getString(R.string.password_json_key)).equals(password)) {

                    if (saveSession) {
                        storeSession(context, email, password);
                    }
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void loadUsersJSONFile(Context context, String usersFileName) {
        JSONHelper jsonHelper = new JSONHelper();
        try {
            this.userObjects = jsonHelper.
                    convertToJSONObject(jsonHelper.loadJSONFile(context, usersFileName)).
                    getJSONArray(context.getString(R.string.users_json_key));

        } catch (JSONException e) {
            e.printStackTrace();
        }
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