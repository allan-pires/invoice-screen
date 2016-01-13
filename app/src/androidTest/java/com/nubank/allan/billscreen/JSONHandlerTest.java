package com.nubank.allan.billscreen;

import com.nubank.allan.billscreen.controller.JSONHandler;
import com.nubank.allan.billscreen.model.Bill;

import junit.framework.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by Allan on 12/01/2016.
 */
public class JSONHandlerTest extends TestCase{

    public static void testGetJSONFromUrl_returnsJSONObject_when_called() throws JSONException {
        JSONArray jobj;
        jobj = JSONHandler.getJSONArrayFromUrl("https://s3-sa-east-1.amazonaws.com/mobile-challenge/bill/bill_new.json");

        Assert.assertNotNull(jobj);
    }

    public static void testParseBill_returnsBill_when_called() throws JSONException, ParseException {
        JSONArray jsonArr;
        jsonArr = JSONHandler.getJSONArrayFromUrl("https://s3-sa-east-1.amazonaws.com/mobile-challenge/bill/bill_new.json");
        JSONObject object = (JSONObject) jsonArr.get(0);
        Bill bill = JSONHandler.parseJSONObjectToBill(object);

        // Bill
        Assert.assertEquals("overdue", bill.getState());
        Assert.assertEquals("55256cd0-10f7-4fd3-a3be-213bfe01857d", bill.getId());
        Assert.assertEquals("03394643200000389339646532300000001745800102", bill.getBarCode());
        Assert.assertEquals("03399.64652  32300.000000  17458.001025  4  64320000038933", bill.getDigitableLine());

        // Summary
        Assert.assertEquals("2015-04-20", bill.getSummary().getDueDate().toString());
        Assert.assertEquals("2015-04-07", bill.getSummary().getCloseDate().toString());
        Assert.assertEquals("2015-03-17", bill.getSummary().getOpenDate().toString());
        Assert.assertEquals(0, bill.getSummary().getPastBalance());
        Assert.assertEquals(38933, bill.getSummary().getTotalBalance());
        Assert.assertEquals(0, bill.getSummary().getInterest());
        Assert.assertEquals(38933, bill.getSummary().getTotalCumulative());
        Assert.assertEquals(38933, bill.getSummary().getPaid());
        Assert.assertEquals(5840, bill.getSummary().getMinPayment());

        // Links
        Assert.assertEquals("https://prod-accounts.nubank.com.br/api/bills/55256cd0-10f7-4fd3-a3be-213bfe01857d", bill.getLinks().get("self"));
        Assert.assertEquals("https://prod-accounts.nubank.com.br/api/bills/55256cd0-10f7-4fd3-a3be-213bfe01857d/boleto/email", bill.getLinks().get("boleto_email"));
        Assert.assertEquals("https://prod-accounts.nubank.com.br/api/bills/55256cd0-10f7-4fd3-a3be-213bfe01857d/boleto/barcode", bill.getLinks().get("barcode"));

        // ItemLine 1
        Assert.assertEquals("2015-03-31", bill.getItens().get(0).getPostDate().toString());
        Assert.assertEquals(3850, bill.getItens().get(0).getAmount());
        Assert.assertEquals("Rest Bolinha", bill.getItens().get(0).getTitle());
        Assert.assertEquals(0, bill.getItens().get(0).getIndex());
        Assert.assertEquals(1, bill.getItens().get(0).getCharges());
        Assert.assertEquals("nuapp://transaction/551585a8-244c-4ab8-9dce-0113d465ad8e", bill.getItens().get(0).getHref());

        // ItemLine 2
        Assert.assertEquals("2015-03-31", bill.getItens().get(1).getPostDate().toString());
        Assert.assertEquals(12505, bill.getItens().get(1).getAmount());
        Assert.assertEquals("Posto de Servico Dinam", bill.getItens().get(1).getTitle());
        Assert.assertEquals(0, bill.getItens().get(1).getIndex());
        Assert.assertEquals(1, bill.getItens().get(1).getCharges());
        Assert.assertEquals("nuapp://transaction/55152410-5315-4aeb-b7f3-263170767191", bill.getItens().get(1).getHref());

        // ItemLine 3
        Assert.assertEquals("2015-04-02", bill.getItens().get(2).getPostDate().toString());
        Assert.assertEquals(4180, bill.getItens().get(2).getAmount());
        Assert.assertEquals("Brunetto Massa e Risot", bill.getItens().get(2).getTitle());
        Assert.assertEquals(0, bill.getItens().get(2).getIndex());
        Assert.assertEquals(1, bill.getItens().get(2).getCharges());
        Assert.assertEquals("nuapp://transaction/551ac6d1-3c44-4f68-a404-788083f00955", bill.getItens().get(2).getHref());

        // ItemLine 4
        Assert.assertEquals("2015-04-07", bill.getItens().get(3).getPostDate().toString());
        Assert.assertEquals(18398, bill.getItens().get(3).getAmount());
        Assert.assertEquals("Pao de Acucar Lj", bill.getItens().get(3).getTitle());
        Assert.assertEquals(0, bill.getItens().get(3).getIndex());
        Assert.assertEquals(1, bill.getItens().get(3).getCharges());
        Assert.assertEquals("nuapp://transaction/55219aaa-2b9d-40bc-a8d3-bb31f14d2382", bill.getItens().get(3).getHref());
    }
}
