package com.nubank.allan.billscreen.controller.handler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nubank.allan.billscreen.controller.task.*;
import com.nubank.allan.billscreen.model.TaskResult;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Allan on 13/01/2016.
 */

public class HTTPConnectionHandler {

    private final String REST_URL = "https://s3-sa-east-1.amazonaws.com/mobile-challenge/bill/bill_new.json";
    private JSONArray response = new JSONArray();
    private Context context;

    // Constructor
    public HTTPConnectionHandler(Context context) {
        this.context = context;
    }

    // Checks if the device has connection with internet
    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
        ExceptionHandler ex = createExceptionHandler();

        // If has internet
        if (isConnectedToInternet()) {
            try {
                // Creates a new RESTTask and executes it
                RESTTask task = createTask();
                URL url = createURL();
                TaskResult data = executeTask(task, url);

                if (data.getCode() != 200){
                    ex.showErrorActivity(context, String.valueOf(data.getCode()));
                    return null;
                }
                String json = data.getResult();
                response = getJSONArrayFromString(json);
            }
            catch (InterruptedException e) {
                ex.showErrorActivity(context, "err_intexec");
            }
            catch (ExecutionException e) {
                ex.showErrorActivity(context, "err_exec");
            }
            catch (JSONException e) {
                ex.showErrorActivity(context, "err_json");
            }
            catch (MalformedURLException e) {
                ex.showErrorActivity(context, "bad_url");
            }
        } else {
            ex.showErrorActivity(context, "no_internet");
        }

        return response;
    }

    public RESTTask createTask(){
        return new RESTTask();
    }

    public TaskResult executeTask(RESTTask task, URL param) throws ExecutionException, InterruptedException {
        return task.execute(param).get();
    }

    public ExceptionHandler createExceptionHandler(){
        return new ExceptionHandler();
    }

    public JSONArray getJSONArrayFromString(String s) throws JSONException {
        return new JSONArray(s);
    }

    public URL createURL() throws MalformedURLException {
        return new URL(REST_URL);
    }
}
