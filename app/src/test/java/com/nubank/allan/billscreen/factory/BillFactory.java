package com.nubank.allan.billscreen.factory;

import com.nubank.allan.billscreen.model.Bill;
import com.nubank.allan.billscreen.model.LineItem;
import com.nubank.allan.billscreen.model.Summary;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by doisl_000 on 2/11/2016.
 */

public class BillFactory {
    /*
        Creates a new Bill Object as follow:
            Summary = SummaryFactory Object
            State = "teststate"
            Id = "0x14"
            BarCode = "01010101"
            DigitableLine = "this is a line"
            Links = HashMap<String, String>
            LineItems = ArrayList<LineItem>
    */
    public static Bill createBillObject(){
        Summary summary = SummaryFactory.createSummaryObject();
        HashMap<String, String> links = new HashMap<>();
        links.put("link1", "href1");
        links.put("link2", "href2");
        return new Bill(summary, "teststate", "0x14", "01010101", "this is a line", links, new ArrayList<LineItem>());
    }
}
