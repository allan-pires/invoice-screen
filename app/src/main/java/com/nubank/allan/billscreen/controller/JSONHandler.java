package com.nubank.allan.billscreen.controller;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Allan on 12/01/2016.
 */

public class JSONHandler {

    static private JSONObject jObj = null;

    public static JSONObject getJSONFromUrl(String s) {

        // Making HTTP request
        try {
            URL url = new URL(s);
            URLConnection connection = url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read lines from file
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();

            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(sb.toString());
            }
            catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return jObj;
    }
}
