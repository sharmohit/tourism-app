package com.project.tourismapp.Helpers;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class JSONHelper {

    public String assetsFileReader(Context context, String fileName) {

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

    public JSONObject readJSONObjectFromFile(Context context, String fileName) throws IOException {
        File file = new File(context.getFilesDir(), fileName);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String response = null;

        if (file.exists()) {

            StringBuffer output = new StringBuffer();
            fileReader = new FileReader(file.getAbsolutePath());
            bufferedReader = new BufferedReader(fileReader);

            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                output.append(line + "\n");
            }

            response = output.toString();
            bufferedReader.close();
            return convertToJSONObject(response);
        }
        else
        {
            return  null;
        }
    }

    public void writeJSONObjectToFile(Context context, String fileName, JSONObject jsonObject) throws IOException {
        File file = new File(context.getFilesDir(), fileName);
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(jsonObject.toString());
        writer.close();
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
