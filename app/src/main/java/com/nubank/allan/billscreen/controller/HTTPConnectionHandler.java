package com.nubank.allan.billscreen.controller;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Allan on 13/01/2016.
 */

public class HTTPConnectionHandler {

    private static String WEBSERVICE_URL = "https://s3-sa-east-1.amazonaws.com/mobile-challenge/bill/bill_new.json";
    private static JSONArray response = new JSONArray();
    private static ExceptionHandler exceptionHandler;
    private Activity owner;

    // Constructor
    public HTTPConnectionHandler(Activity owner) {
        this.owner = owner;
        this.exceptionHandler = new ExceptionHandler(owner);
    }

    // Checks if the device has connection with internet
    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) owner.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    // Gets a JSONArray from the Webservice
    public JSONArray getJSONArrayData() {

        // If has internet
        if (isConnectedToInternet()) {
            try {

                // Get the data
                ConsumeREST rest = new ConsumeREST(owner);
                AsyncResult data = rest.execute(WEBSERVICE_URL).get();
                if (data.getCode() != 200){
                    exceptionHandler.showErrorActivity(owner.getBaseContext(), String.valueOf(data.getCode()));
                    return null;
                }
                response = new JSONArray(data.getResult());
            }
            catch (InterruptedException e) {
                exceptionHandler.showErrorActivity(owner.getApplicationContext(), "err_intexec");
            }
            catch (ExecutionException e) {
                exceptionHandler.showErrorActivity(owner.getApplicationContext(), "err_exec");
            }
            catch (JSONException e) {
                exceptionHandler.showErrorActivity(owner.getApplicationContext(), "err_json");
            }
        } else {
            exceptionHandler.showErrorActivity(owner.getApplicationContext(), "no_internet");
        }

        return response;
    }

    // Result class for async task, returns the data and the code of the transaction
    class AsyncResult {
        private String result;
        private int code;

        public AsyncResult(String result, int code) {
            this.result = result;
            this.code = code;
        }

        public String getResult() {
            return result;
        }

        public int getCode() {
            return code;
        }
    }

    // Async task that consumes the REST API and gets the data
    class ConsumeREST extends AsyncTask<String, String, AsyncResult> {

        private Activity parent;
        private Exception exception;

        public ConsumeREST(Activity activity){
            this.parent = activity;
        }

        @Override
        protected AsyncResult doInBackground(String... params) {

            // Consume REST API to get the JSON
            String url_string = params[0];
            StringBuilder sb = new StringBuilder();

            // Code 200 = OK
            int statusCode = 200;

            try {
                // Try to open a connection
                URL url = new URL(url_string);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Refresh the status code
                statusCode = connection.getResponseCode();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;

                // Read lines from file
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                reader.close();
            }
            catch (MalformedURLException e) {
                return new AsyncResult(sb.toString(), statusCode);
            }
            catch (IOException e) {
                return new AsyncResult(sb.toString(), statusCode);
            }

            return new AsyncResult(sb.toString(), statusCode);
        }
    }
}
