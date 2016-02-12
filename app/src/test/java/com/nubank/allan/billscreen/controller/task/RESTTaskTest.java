package com.nubank.allan.billscreen.controller.task;

import com.nubank.allan.billscreen.controller.task.RESTTask;
import com.nubank.allan.billscreen.factory.BillJSONArrayFactory;
import com.nubank.allan.billscreen.factory.TaskResultFactory;
import com.nubank.allan.billscreen.model.TaskResult;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.security.auth.callback.Callback;

/**
 * Created by doisl_000 on 1/24/2016.
 */
public class RESTTaskTest extends TestCase {
    private URL params;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        params = new URL("https://s3-sa-east-1.amazonaws.com/mobile-challenge/bill/bill_new.json");
    }

    public void testCreateConnection_returnsNewHttpConnection_whenCalled() throws IOException {
        RESTTask task = new RESTTask();
        assertNotNull(task.createConnection(params));
    }
}
