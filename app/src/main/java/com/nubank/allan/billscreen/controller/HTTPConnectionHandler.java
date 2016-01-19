package com.nubank.allan.billscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.nubank.allan.billscreen.view.ErrorActivity;
import com.nubank.allan.billscreen.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

/**
 * Created by Allan on 13/01/2016.
 */

public class HTTPConnectionHandler{

    private static String WEBSERVICE_URL = "https://s3-sa-east-1.amazonaws.com/mobile-challenge/bill/bill_new.json";
    private static JSONArray response = new JSONArray();
    private Context context;

    public HTTPConnectionHandler(Context context){
        this.context = context;
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public JSONArray getJSONArrayData(){

        // try parse the string to a JSON object
        if (isConnectedToInternet()){
            try {
                ConsumeREST rest = new ConsumeREST();
                String data = rest.execute(WEBSERVICE_URL).get();
                response = new JSONArray(data);
            }
            catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.getMessage());
                ExceptionHandler.showErrorActivity(context, e.getMessage());

            } catch (InterruptedException e) {
                Log.d("AsyncTask", "Interrupted execution " + e.getMessage());
                ExceptionHandler.showErrorActivity(context, e.getMessage());

            } catch (ExecutionException e) {
                Log.d("AsyncTask", "Error executing " + e.getMessage());
                ExceptionHandler.showErrorActivity(context, e.getMessage());
            }
        }
        else{
            ExceptionHandler.showErrorActivity(context, "Parece que você está sem internet!\nPor favor, verifique a sua conexão e tente novamente.");
        }

        return response;
    }
}
    class ConsumeREST extends AsyncTask<String, String, String> {

        private Exception exception;

        @Override
        protected String doInBackground(String... params) {

            // Consume REST API to get the JSON
            String url_string = params[0];
            StringBuilder sb = new StringBuilder();

            try {
                URL url = new URL(url_string);
                URLConnection connection = url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;

                // Read lines from file
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                reader.close();
            }
            catch (MalformedURLException e) {
                Log.d("ERROR: ", e.getMessage());
                exception = e;
                return null;
            }
            catch (IOException e) {
                Log.d("ERROR: ", e.getMessage());
                exception = e;
                return null;
            }

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String result){
        }
}
