package com.nubank.allan.billscreen.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Allan on 11/01/2016.
 */
public class Bill {

    private Summary summary;
    private String state;
    private String id;
    private String barCode;
    private String digitableLine;
    private HashMap<String, String> links;
    private ArrayList<LineItem> items;

    public Bill(){
    }

    public Bill(Summary summary, String state, String id, String barCode, String digitableLine, HashMap<String, String> links, ArrayList<LineItem> items) {
        this.summary = summary;
        this.state = state;
        this.id = id;
        this.barCode = barCode;
        this.digitableLine = digitableLine;
        this.links = links;
        this.items = items;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getDigitableLine() {
        return digitableLine;
    }

    public void setDigitableLine(String digitableLine) {
        this.digitableLine = digitableLine;
    }

    public HashMap<String, String> getLinks() {
        return links;
    }

    public void setLinks(HashMap<String, String> links) {
        this.links = links;
    }

    public ArrayList<LineItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<LineItem> items) {
        this.items = items;
    }

}
