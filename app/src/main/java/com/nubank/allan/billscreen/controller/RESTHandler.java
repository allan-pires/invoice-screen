package com.nubank.allan.billscreen.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Allan on 13/01/2016.
 */
public class RESTHandler extends AsyncTask<String, String, JSONArray> {

    @Override
    protected JSONArray doInBackground(String... params) {

        // Consume REST API to get the JSON
        String url_string = "https://s3-sa-east-1.amazonaws.com/mobile-challenge/bill/bill_new.json";
        JSONArray jsonArray = new JSONArray();

        try {
            URL url = new URL(url_string);
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
                jsonArray = new JSONArray(sb.toString());
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

        return jsonArray;
    }
}
