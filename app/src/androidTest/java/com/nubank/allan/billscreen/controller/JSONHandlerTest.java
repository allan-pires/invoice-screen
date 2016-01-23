package com.nubank.allan.billscreen.controller;

import android.app.Activity;
import android.content.Context;
import android.test.InstrumentationTestCase;

import com.nubank.allan.billscreen.model.Bill;
import com.nubank.allan.billscreen.model.LineItem;
import com.nubank.allan.billscreen.model.Summary;
import com.nubank.allan.billscreen.view.MainActivity;

import junit.framework.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Allan on 12/01/2016.
 */
public class JSONHandlerTest extends InstrumentationTestCase{

    Context context;
    MainActivity main;
    HTTPConnectionHandler async;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty(
                "dexmaker.dexcache",
                getInstrumentation().getTargetContext().getCacheDir().getPath());
        context = Mockito.mock(Context.class);
        main = Mockito.mock(MainActivity.class);
        async = Mockito.mock(HTTPConnectionHandler.class);
        Mockito.when(main.getApplicationContext()).thenReturn(context);
        Mockito.when(async.getJSONArrayData()).thenCallRealMethod();
        Mockito.when(async.isConnectedToInternet()).thenReturn(true);
    }

    public void testParseJSONArrayToLineItem_returnsLineItem_whenCalled() throws JSONException, ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ExecutionException, InterruptedException {
        JSONArray result = async.getJSONArrayData();
        JSONObject object = (JSONObject) result.get(0);
        JSONHandler jsonHandler = new JSONHandler(main);

        Method method = JSONHandler.class.getDeclaredMethod("parseJSONArrayToLineItem", JSONArray.class);
        method.setAccessible(true);
        ArrayList<LineItem> lineItems = (ArrayList<LineItem>) method.invoke(jsonHandler, object.getJSONObject("bill").getJSONArray("line_items"));

        // ItemLine 1
        Assert.assertEquals("2015-03-31", lineItems.get(0).getPostDate().toString());
        Assert.assertEquals(38.50, lineItems.get(0).getAmount());
        Assert.assertEquals("Rest Bolinha", lineItems.get(0).getTitle());
        Assert.assertEquals(0, lineItems.get(0).getIndex());
        Assert.assertEquals(1, lineItems.get(0).getCharges());
        Assert.assertEquals("nuapp://transaction/551585a8-244c-4ab8-9dce-0113d465ad8e", lineItems.get(0).getHref());

        // ItemLine 2
        Assert.assertEquals("2015-03-31", lineItems.get(1).getPostDate().toString());
        Assert.assertEquals(125.05, lineItems.get(1).getAmount());
        Assert.assertEquals("Posto de Servico Dinam", lineItems.get(1).getTitle());
        Assert.assertEquals(0, lineItems.get(1).getIndex());
        Assert.assertEquals(1, lineItems.get(1).getCharges());
        Assert.assertEquals("nuapp://transaction/55152410-5315-4aeb-b7f3-263170767191", lineItems.get(1).getHref());

        // ItemLine 3
        Assert.assertEquals("2015-04-02", lineItems.get(2).getPostDate().toString());
        Assert.assertEquals(41.80, lineItems.get(2).getAmount());
        Assert.assertEquals("Brunetto Massa e Risot", lineItems.get(2).getTitle());
        Assert.assertEquals(0, lineItems.get(2).getIndex());
        Assert.assertEquals(1, lineItems.get(2).getCharges());
        Assert.assertEquals("nuapp://transaction/551ac6d1-3c44-4f68-a404-788083f00955", lineItems.get(2).getHref());

        // ItemLine 4
        Assert.assertEquals("2015-04-07", lineItems.get(3).getPostDate().toString());
        Assert.assertEquals(183.98, lineItems.get(3).getAmount());
        Assert.assertEquals("Pao de Acucar Lj", lineItems.get(3).getTitle());
        Assert.assertEquals(0, lineItems.get(3).getIndex());
        Assert.assertEquals(1, lineItems.get(3).getCharges());
        Assert.assertEquals("nuapp://transaction/55219aaa-2b9d-40bc-a8d3-bb31f14d2382", lineItems.get(3).getHref());
    }

    public void testParseJSONObjectToSummary_returnsSummary_when_called() throws JSONException, ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ExecutionException, InterruptedException {
        JSONArray result = async.getJSONArrayData();
        JSONObject object = (JSONObject) result.get(0);
        JSONHandler jsonHandler = new JSONHandler(main);

        Method method = JSONHandler.class.getDeclaredMethod("parseJSONObjectToSummary", JSONObject.class);
        method.setAccessible(true);
        Summary summary = (Summary) method.invoke(jsonHandler, object.getJSONObject("bill").getJSONObject("summary"));

        // Summary
        Assert.assertEquals("2015-04-20", summary.getDueDate().toString());
        Assert.assertEquals("2015-04-07", summary.getCloseDate().toString());
        Assert.assertEquals("2015-03-17", summary.getOpenDate().toString());
        Assert.assertEquals(0.0, summary.getPastBalance());
        Assert.assertEquals(389.33, summary.getTotalBalance());
        Assert.assertEquals(0.0, summary.getInterest());
        Assert.assertEquals(389.33, summary.getTotalCumulative());
        Assert.assertEquals(389.33, summary.getPaid());
        Assert.assertEquals(58.40, summary.getMinPayment());
    }

    public void testParseJSONObjectToBill_returnsBill_whenCalled() throws JSONException, ParseException, ExecutionException, InterruptedException {
        JSONArray result = async.getJSONArrayData();
        JSONObject object = (JSONObject) result.get(0);
        JSONHandler jsonHandler = new JSONHandler(main);
        Bill bill = jsonHandler.parseJSONObjectToBill(object);

        // Bill
        Assert.assertEquals("overdue", bill.getState());
        Assert.assertEquals("55256cd0-10f7-4fd3-a3be-213bfe01857d", bill.getId());
        Assert.assertEquals("03394643200000389339646532300000001745800102", bill.getBarCode());
        Assert.assertEquals("03399.64652  32300.000000  17458.001025  4  64320000038933", bill.getDigitableLine());

        // Summary
        Assert.assertEquals("2015-04-20", bill.getSummary().getDueDate().toString());
        Assert.assertEquals("2015-04-07", bill.getSummary().getCloseDate().toString());
        Assert.assertEquals("2015-03-17", bill.getSummary().getOpenDate().toString());
        Assert.assertEquals(0.0, bill.getSummary().getPastBalance());
        Assert.assertEquals(389.33, bill.getSummary().getTotalBalance());
        Assert.assertEquals(0.0, bill.getSummary().getInterest());
        Assert.assertEquals(389.33, bill.getSummary().getTotalCumulative());
        Assert.assertEquals(389.33, bill.getSummary().getPaid());
        Assert.assertEquals(58.40, bill.getSummary().getMinPayment());

        // Links
        Assert.assertEquals("https://prod-accounts.nubank.com.br/api/bills/55256cd0-10f7-4fd3-a3be-213bfe01857d", bill.getLinks().get("self"));
        Assert.assertEquals("https://prod-accounts.nubank.com.br/api/bills/55256cd0-10f7-4fd3-a3be-213bfe01857d/boleto/email", bill.getLinks().get("boleto_email"));
        Assert.assertEquals("https://prod-accounts.nubank.com.br/api/bills/55256cd0-10f7-4fd3-a3be-213bfe01857d/boleto/barcode", bill.getLinks().get("barcode"));

        // ItemLine 1
        Assert.assertEquals("2015-03-31", bill.getItems().get(0).getPostDate().toString());
        Assert.assertEquals(38.50, bill.getItems().get(0).getAmount());
        Assert.assertEquals("Rest Bolinha", bill.getItems().get(0).getTitle());
        Assert.assertEquals(0, bill.getItems().get(0).getIndex());
        Assert.assertEquals(1, bill.getItems().get(0).getCharges());
        Assert.assertEquals("nuapp://transaction/551585a8-244c-4ab8-9dce-0113d465ad8e", bill.getItems().get(0).getHref());

        // ItemLine 2
        Assert.assertEquals("2015-03-31", bill.getItems().get(1).getPostDate().toString());
        Assert.assertEquals(125.05, bill.getItems().get(1).getAmount());
        Assert.assertEquals("Posto de Servico Dinam", bill.getItems().get(1).getTitle());
        Assert.assertEquals(0, bill.getItems().get(1).getIndex());
        Assert.assertEquals(1, bill.getItems().get(1).getCharges());
        Assert.assertEquals("nuapp://transaction/55152410-5315-4aeb-b7f3-263170767191", bill.getItems().get(1).getHref());

        // ItemLine 3
        Assert.assertEquals("2015-04-02", bill.getItems().get(2).getPostDate().toString());
        Assert.assertEquals(41.80, bill.getItems().get(2).getAmount());
        Assert.assertEquals("Brunetto Massa e Risot", bill.getItems().get(2).getTitle());
        Assert.assertEquals(0, bill.getItems().get(2).getIndex());
        Assert.assertEquals(1, bill.getItems().get(2).getCharges());
        Assert.assertEquals("nuapp://transaction/551ac6d1-3c44-4f68-a404-788083f00955", bill.getItems().get(2).getHref());

        // ItemLine 4
        Assert.assertEquals("2015-04-07", bill.getItems().get(3).getPostDate().toString());
        Assert.assertEquals(183.98, bill.getItems().get(3).getAmount());
        Assert.assertEquals("Pao de Acucar Lj", bill.getItems().get(3).getTitle());
        Assert.assertEquals(0, bill.getItems().get(3).getIndex());
        Assert.assertEquals(1, bill.getItems().get(3).getCharges());
        Assert.assertEquals("nuapp://transaction/55219aaa-2b9d-40bc-a8d3-bb31f14d2382", bill.getItems().get(3).getHref());
    }
}
