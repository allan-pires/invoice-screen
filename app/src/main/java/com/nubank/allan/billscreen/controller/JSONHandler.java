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

/**
 * Created by Allan on 12/01/2016.
 */

public class JSONHandler {

    static private JSONArray jsonArray = null;

    // Get JSONArray from WebService
    public static JSONArray getJSONArrayFromUrl(String s) {

        // Consume REST API to get the JSON
        try {
            URL url = new URL(s);
            URLConnection connection = url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read lines from file
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();

            // try parse the string to a JSON object
            try {
                jsonArray = new JSONArray(sb.toString());
            }
            catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    // Parses JSONObject -> Summary
    private static Summary parseJSONObjectToSummary(JSONObject summaryObject){

        Summary summary = new Summary();

        try {
            // Summary - Date handling
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date date = new java.sql.Date(format.parse(summaryObject.getString("close_date")).getTime());
            summary.setCloseDate(date);
            date = new java.sql.Date(format.parse(summaryObject.getString("due_date")).getTime());
            summary.setDueDate(date);
            date = new java.sql.Date(format.parse(summaryObject.getString("open_date")).getTime());
            summary.setOpenDate(date);

            // Summary - Get rest of the data from JSON
            summary.setPastBalance(Integer.parseInt(summaryObject.getString("past_balance")));
            summary.setTotalBalance(Integer.parseInt(summaryObject.getString("total_balance")));
            summary.setTotalCumulative(Integer.parseInt(summaryObject.getString("total_cumulative")));
            summary.setInterest(Integer.parseInt(summaryObject.getString("interest")));
            summary.setPaid(Integer.parseInt(summaryObject.getString("paid")));
            summary.setMinPayment(Integer.parseInt(summaryObject.getString("minimum_payment")));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
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
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date date = new java.sql.Date(format.parse(lineItemObject.get("post_date").toString()).getTime());
                temp.setPostDate(date);
                temp.setAmount(Integer.parseInt(lineItemObject.get("amount").toString()));
                temp.setTitle(lineItemObject.get("title").toString());
                temp.setIndex(Integer.parseInt(lineItemObject.get("index").toString()));
                temp.setCharges(Integer.parseInt(lineItemObject.get("charges").toString()));
                temp.setHref(lineItemObject.get("href").toString());

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
        links.put("self", linksObject.getJSONObject("self").getString("href"));
        links.put("boleto_email", linksObject.getJSONObject("boleto_email").getString("href"));
        links.put("barcode", linksObject.getJSONObject("barcode").getString("href"));

        // Bill attributes
        bill.setState(billObject.getString("state"));
        bill.setId(billObject.getString("id"));
        bill.setBarCode(billObject.getString("barcode"));
        bill.setDigitableLine(billObject.getString("linha_digitavel"));
        bill.setSummary(summary);
        bill.setLinks(links);
        bill.setItems(lineItems);

        return bill;
    }
}
