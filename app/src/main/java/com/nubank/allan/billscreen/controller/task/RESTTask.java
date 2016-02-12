package com.nubank.allan.billscreen.controller.task;

import android.os.AsyncTask;

import com.nubank.allan.billscreen.model.TaskResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by doisl_000 on 1/24/2016.
 */
// Async task that consumes the REST API and gets the data
public class RESTTask extends AsyncTask<URL, String, TaskResult> {

    @Override
    protected TaskResult doInBackground(URL... params) {

        // Consume REST API to get the JSON
        URL url = params[0];
        StringBuilder sb = new StringBuilder();
        int statusCode = 200; // Code 200 = OK

        if (url != null){
            try {

                HttpURLConnection connection = createConnection(url);
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
            catch (IOException e) {
                return new TaskResult(sb.toString(), statusCode);
            }
        }
        else{
            sb.append("");
            statusCode = 500; // Code 500 = InternalError
        }

        return new TaskResult(sb.toString(), statusCode);
    }

    public HttpURLConnection createConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }
}
