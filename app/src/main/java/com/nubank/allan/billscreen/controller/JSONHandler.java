package com.nubank.allan.billscreen.controller;

import android.util.Log;

import com.nubank.allan.billscreen.model.Bill;
import com.nubank.allan.billscreen.model.LineItem;
import com.nubank.allan.billscreen.model.Summary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by Allan on 12/01/2016.
 */

public class JSONHandler {

    static private JSONArray jsonArray = null;

    // Parses JSONObject -> Summary
    private static Summary parseJSONObjectToSummary(JSONObject summaryObject){

        Summary summary = new Summary();

        try {
            // Summary - Date handling
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date date;

            // Get the data from JSON
            if (summaryObject.has("close_date")){
                date = new java.sql.Date(format.parse(summaryObject.getString("close_date")).getTime());
                summary.setCloseDate(date);
            }
            if (summaryObject.has("due_date")){
                date = new java.sql.Date(format.parse(summaryObject.optString("due_date")).getTime());
                summary.setDueDate(date);
            }
            if (summaryObject.has("open_date")){
                date = new java.sql.Date(format.parse(summaryObject.optString("open_date")).getTime());
                summary.setOpenDate(date);
            }
            if (summaryObject.has("past_balance")){
                summary.setPastBalance(summaryObject.getInt("past_balance"));
            }
            if (summaryObject.has("total_balance")){
                summary.setTotalBalance(summaryObject.getInt("total_balance"));
            }
            if (summaryObject.has("total_cumulative")){
                summary.setTotalCumulative(summaryObject.getInt("total_cumulative"));
            }
            if (summaryObject.has("interest")){
                summary.setInterest(summaryObject.getInt("interest"));
            }
            if (summaryObject.has("paid")){
                summary.setPaid(summaryObject.getInt("paid"));
            }
            if (summaryObject.has("minimum_payment")){
                summary.setMinPayment(summaryObject.getInt("minimum_payment"));
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return summary;
    }

    // Parses JSONObject -> ArrayList<LineItem>
    private static ArrayList<LineItem> parseJSONArrayToLineItem(JSONArray lineItemArray){

        // Variables
        ArrayList<LineItem> lineItems = new ArrayList<>();
        int size = lineItemArray.length();

        // Foreach LineItem in JSONArray
        for (int i = 0; i < size; i++){
            try{
                JSONObject lineItemObject = lineItemArray.getJSONObject(i);
                LineItem temp = new LineItem();

                // Get data from JSON and set temp variable
                if (lineItemObject.has("post_date")){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.sql.Date date = new java.sql.Date(format.parse(lineItemObject.get("post_date").toString()).getTime());
                    temp.setPostDate(date);
                }
                if (lineItemObject.has("amount")) {
                    temp.setAmount(lineItemObject.getInt("amount"));
                }
                if (lineItemObject.has("title")) {
                    temp.setTitle(lineItemObject.get("title").toString());
                }
                if (lineItemObject.has("index")) {
                    temp.setIndex(lineItemObject.getInt("index"));
                }
                if (lineItemObject.has("charges")) {
                    temp.setCharges(lineItemObject.getInt("charges"));
                }
                if (lineItemObject.has("href")) {
                    temp.setHref(lineItemObject.getString("href"));
                }
                // Adds temp variable to ArrayList
                lineItems.add(temp);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return lineItems;
    }

    // Parses JSONObject -> Bill
    public static Bill parseJSONObjectToBill(JSONObject json) throws JSONException, ParseException {

        // Final type objects
        Bill bill = new Bill();
        Summary summary = new Summary();
        ArrayList<LineItem> lineItems = new ArrayList<>();
        HashMap<String, String> links = new HashMap<>();

        // JSONObjects from file
        JSONObject billObject = json.getJSONObject("bill");
        JSONObject summaryObject = billObject.getJSONObject("summary");
        JSONObject linksObject = billObject.getJSONObject("_links");
        JSONArray lineItemArray = billObject.getJSONArray("line_items");

        // Summary
        summary = parseJSONObjectToSummary(summaryObject);

        // LineItem
        lineItems = parseJSONArrayToLineItem(lineItemArray);

        // Links
        if (linksObject.has("self")) {
            links.put("self", linksObject.getJSONObject("self").optString("href"));
        }
        if (linksObject.has("boleto_email")) {
            links.put("boleto_email", linksObject.getJSONObject("boleto_email").optString("href"));
        }
        if (linksObject.has("barcode")) {
            links.put("barcode", linksObject.getJSONObject("barcode").optString("href"));
        }

        // Bill attributes
        bill.setState(billObject.optString("state"));
        bill.setId(billObject.optString("id"));
        bill.setBarCode(billObject.optString("barcode"));
        bill.setDigitableLine(billObject.optString("linha_digitavel"));
        bill.setSummary(summary);
        bill.setLinks(links);
        bill.setItems(lineItems);

        return bill;
    }

    // Do all the stuff from up
    public static ArrayList<Bill> getBillsFromUrl() throws JSONException, ParseException {
        ArrayList<Bill> bills = new ArrayList<>();

        JSONArray jArray = null;
        try {
            jArray = new RESTHandler().execute("").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        int size = jArray.length();
        for (int i = 0; i < size; i++){
            JSONObject jObject = (JSONObject) jArray.get(i);
            bills.add(parseJSONObjectToBill(jObject));
        }

        return bills;
    }
}
