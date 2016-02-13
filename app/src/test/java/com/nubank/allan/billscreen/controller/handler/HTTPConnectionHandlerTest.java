package com.nubank.allan.billscreen.controller.handler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.test.InstrumentationTestCase;

import com.nubank.allan.billscreen.controller.handler.ExceptionHandler;
import com.nubank.allan.billscreen.controller.handler.HTTPConnectionHandler;
import com.nubank.allan.billscreen.controller.task.RESTTask;
import com.nubank.allan.billscreen.factory.BillJSONArrayFactory;
import com.nubank.allan.billscreen.factory.TaskResultFactory;
import com.nubank.allan.billscreen.model.TaskResult;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by doisl_000 on 1/16/2016.
 */
public class HTTPConnectionHandlerTest extends TestCase {

    private HTTPConnectionHandler connectionHandler;
    private ConnectivityManager connectivityManager;
    private ExceptionHandler ex;
    private RESTTask task;
    private Context context;
    private String WEBSERVICE_URL = "https://s3-sa-east-1.amazonaws.com/mobile-challenge/bill/bill_new.json";
    private URL url;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        context = Mockito.mock(Context.class);
        task =  Mockito.mock(RESTTask.class);
        ex = Mockito.mock(ExceptionHandler.class);
        connectionHandler = Mockito.mock(HTTPConnectionHandler.class);
        connectivityManager = Mockito.mock(ConnectivityManager.class);
        url = new URL(WEBSERVICE_URL);
    }

    public void testGetJSONArrayData_returnsJSONArray_when_Called() throws ExecutionException, InterruptedException, JSONException, MalformedURLException {
        JSONArray expected = BillJSONArrayFactory.createBillJSONArray();
        URL url = new URL(WEBSERVICE_URL);
        RESTTask task = new RESTTask();
        TaskResult result = TaskResultFactory.createRealSucessTaskResultObject();

        when(connectionHandler.getJSONArrayData()).thenCallRealMethod();
        when(connectionHandler.isConnectedToInternet()).thenReturn(true);
        when(connectionHandler.createURL()).thenReturn(url);
        when(connectionHandler.createTask()).thenReturn(task);
        when(connectionHandler.executeTask(task, url)).thenReturn(result);
        when(connectionHandler.getJSONArrayFromString(result.getResult())).thenReturn(expected);
        JSONArray returned = connectionHandler.getJSONArrayData();

        assertEquals(expected.get(0).toString(), returned.get(0).toString());
        assertEquals(expected.get(1).toString(), returned.get(1).toString());
        assertEquals(expected.get(2).toString(), returned.get(2).toString());
        assertEquals(expected.get(3).toString(), returned.get(3).toString());
    }

    public void testGetJSONArrayData_showsErrorActivity_whenNoConnectionAvailable() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        when(connectionHandler.getJSONArrayData()).thenCallRealMethod();
        when(connectionHandler.isConnectedToInternet()).thenReturn(false);
        when(connectionHandler.createExceptionHandler()).thenReturn(ex);

        connectionHandler.getJSONArrayData();
        verify(connectionHandler, times(1)).isConnectedToInternet();
        verify(connectionHandler, times(1)).createExceptionHandler();
        verify(ex, times(1)).showErrorActivity(any(Context.class), eq("no_internet"));
    }

    public void testGetJSONArrayData_showsErrorActivity_whenInvalidURL() throws Exception {
        when(connectionHandler.getJSONArrayData()).thenCallRealMethod();
        when(connectionHandler.isConnectedToInternet()).thenReturn(true);
        when(connectionHandler.createURL()).thenThrow(new MalformedURLException());
        when(connectionHandler.createExceptionHandler()).thenReturn(ex);

        connectionHandler.getJSONArrayData();
        verify(connectionHandler, times(1)).isConnectedToInternet();
        verify(connectionHandler, times(1)).createExceptionHandler();
        verify(ex, times(1)).showErrorActivity(any(Context.class), eq("bad_url"));
    }

    public void testGetJSONArrayData_showsErrorActivity_whenInvalidJSON() throws Exception {
        TaskResult result = TaskResultFactory.createRealSucessTaskResultObject();

        when(connectionHandler.getJSONArrayData()).thenCallRealMethod();
        when(connectionHandler.isConnectedToInternet()).thenReturn(true);
        when(connectionHandler.createTask()).thenReturn(task);
        when(connectionHandler.createURL()).thenReturn(url);
        when(connectionHandler.executeTask(task, url)).thenReturn(result);
        when(connectionHandler.getJSONArrayFromString((String) notNull())).thenThrow(new JSONException("err_json"));
        when(connectionHandler.createExceptionHandler()).thenReturn(ex);

        connectionHandler.getJSONArrayData();
        verify(connectionHandler, times(1)).isConnectedToInternet();
        verify(connectionHandler, times(1)).createExceptionHandler();
        verify(ex, times(1)).showErrorActivity(any(Context.class), eq("err_json"));
    }

    public void testGetJSONArrayData_showsErrorActivity_whenInterruptedException() throws Exception {
        when(connectionHandler.getJSONArrayData()).thenCallRealMethod();
        when(connectionHandler.isConnectedToInternet()).thenReturn(true);
        when(connectionHandler.createTask()).thenReturn(task);
        when(connectionHandler.createURL()).thenReturn(url);
        when(connectionHandler.executeTask(task, url)).thenThrow(new InterruptedException());
        when(connectionHandler.createExceptionHandler()).thenReturn(ex);

        connectionHandler.getJSONArrayData();
        verify(connectionHandler, times(1)).isConnectedToInternet();
        verify(connectionHandler, times(1)).createExceptionHandler();
        verify(ex, times(1)).showErrorActivity(any(Context.class), eq("err_intexec"));
    }

    public void testGetJSONArrayData_showsErrorActivity_whenErrorCode4xx() throws Exception {
        TaskResult result = TaskResultFactory.create4xxTaskResultObject();
        when(connectionHandler.getJSONArrayData()).thenCallRealMethod();
        when(connectionHandler.isConnectedToInternet()).thenReturn(true);
        when(connectionHandler.createTask()).thenReturn(task);
        when(connectionHandler.createURL()).thenReturn(url);
        when(connectionHandler.executeTask(task, url)).thenReturn(result);
        when(connectionHandler.createExceptionHandler()).thenReturn(ex);

        connectionHandler.getJSONArrayData();
        verify(connectionHandler, times(1)).isConnectedToInternet();
        verify(connectionHandler, times(1)).createExceptionHandler();
        verify(ex, times(1)).showErrorActivity(any(Context.class), eq("404"));
    }

    public void testGetJSONArrayData_showsErrorActivity_whenErrorCode5xx() throws Exception {
        TaskResult result = TaskResultFactory.create5xxTaskResultObject();
        when(connectionHandler.getJSONArrayData()).thenCallRealMethod();
        when(connectionHandler.isConnectedToInternet()).thenReturn(true);
        when(connectionHandler.createTask()).thenReturn(task);
        when(connectionHandler.createURL()).thenReturn(url);
        when(connectionHandler.executeTask(task, url)).thenReturn(result);
        when(connectionHandler.createExceptionHandler()).thenReturn(ex);

        connectionHandler.getJSONArrayData();
        verify(connectionHandler, times(1)).isConnectedToInternet();
        verify(connectionHandler, times(1)).createExceptionHandler();
        verify(ex, times(1)).showErrorActivity(any(Context.class), eq("503"));
    }

    public void testIsConnectedToInternet_returnsTrue_whenConnected() {
        NetworkInfo netInfo = Mockito.mock(NetworkInfo.class);
        NetworkInfo[] allInfo = new NetworkInfo[1];
        allInfo[0] = netInfo;

        when(connectionHandler.isConnectedToInternet()).thenCallRealMethod();
        when(connectionHandler.getConnectivityService()).thenReturn(connectivityManager);
        when(connectivityManager.getAllNetworkInfo()).thenReturn(allInfo);
        when(netInfo.getState()).thenReturn(NetworkInfo.State.CONNECTED);

        Boolean connected = connectionHandler.isConnectedToInternet();
        verify(connectionHandler, times(1)).getConnectivityService();
        verify(connectivityManager, times(1)).getAllNetworkInfo();
        assertEquals(true, connected.booleanValue());
    }

    public void testIsConnectedToInternet_returnsFalse_whenNotConnected() {
        NetworkInfo netInfo = Mockito.mock(NetworkInfo.class);
        NetworkInfo[] allInfo = new NetworkInfo[1];
        allInfo[0] = netInfo;

        when(connectionHandler.isConnectedToInternet()).thenCallRealMethod();
        when(connectionHandler.getConnectivityService()).thenReturn(connectivityManager);
        when(connectivityManager.getAllNetworkInfo()).thenReturn(allInfo);
        when(netInfo.getState()).thenReturn(NetworkInfo.State.DISCONNECTED);

        Boolean connected = connectionHandler.isConnectedToInternet();
        verify(connectionHandler, times(1)).getConnectivityService();
        verify(connectivityManager, times(1)).getAllNetworkInfo();
        assertEquals(false, connected.booleanValue());
    }

    public void testCreateTask_returnsRestTaskObject_whenCalled(){
        when(connectionHandler.createTask()).thenCallRealMethod();

        RESTTask task = connectionHandler.createTask();
        assertNotNull(task);
    }

    public void testCreateExceptionHandler_returnsExceptionHandlerObject_whenCalled(){
        when(connectionHandler.createExceptionHandler()).thenCallRealMethod();
        ExceptionHandler ex = connectionHandler.createExceptionHandler();

        assertNotNull(ex);
    }

    public void testGetJSONArrayFromString_returnsJSONArrayObject_whenCalled() throws JSONException {
        JSONArray expected = BillJSONArrayFactory.createBillJSONArray();
        JSONArray returned;

        when(connectionHandler.getJSONArrayFromString(expected.toString())).thenCallRealMethod();

        returned = connectionHandler.getJSONArrayFromString(expected.toString());
        assertEquals(expected.toString(), returned.toString());
    }

    public void testCreateURL_returnsURLObject_whenCalled() throws MalformedURLException {
        URL returned;
        when(connectionHandler.createURL()).thenCallRealMethod();
        returned = connectionHandler.createURL();

        assertEquals(url, returned);
    }

    public void testGetConnectivityService_returnsConnectivityService_whenCalled(){
        HTTPConnectionHandler connectionHandler = new HTTPConnectionHandler(context);
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);

        ConnectivityManager conn = connectionHandler.getConnectivityService();
        assertNotNull(conn);
    }
}