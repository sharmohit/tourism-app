package com.project.tourismapp.Helpers;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JSONHelper {

    public String loadJSONFile(Context context, String fileName) {

        try {
            InputStream fileData = context.getAssets().open(fileName);

            byte[] buffer = new byte[fileData.available()];
            fileData.read(buffer);
            fileData.close();

            return new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject convertToJSONObject(String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
