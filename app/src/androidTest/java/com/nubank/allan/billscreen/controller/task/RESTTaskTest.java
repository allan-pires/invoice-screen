package com.nubank.allan.billscreen.controller.task;

import android.test.InstrumentationTestCase;

import com.nubank.allan.billscreen.controller.task.RESTTask;
import com.nubank.allan.billscreen.factory.TaskResultFactory;
import com.nubank.allan.billscreen.model.TaskResult;

import junit.framework.TestCase;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by doisl_000 on 1/24/2016.
 */
public class RESTTaskTest extends InstrumentationTestCase {
    private URL params;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        params = new URL("https://s3-sa-east-1.amazonaws.com/mobile-challenge/bill/bill_new.json");
    }

    public void testExecute_returnsTaskResult_whenCalled() throws MalformedURLException, ExecutionException, InterruptedException, JSONException {
        RESTTask task = new RESTTask();

        TaskResult expected = TaskResultFactory.createRealSucessTaskResultObject();
        TaskResult result = task.execute(params).get();

        assertNotNull(result);
        assertNotSame("", result.getResult());
        assertEquals(expected.getCode(), result.getCode());
    }

    public void testExecute_returnsErrorCode_whenNullURL() throws ExecutionException, InterruptedException {
        RESTTask task = new RESTTask();

        TaskResult expected = new TaskResult("", 500);
        TaskResult result = task.execute((URL) null).get();

        assertEquals(expected.getCode(), result.getCode());
        assertEquals(expected.getResult(), result.getResult());
    }
}
