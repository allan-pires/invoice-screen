package com.nubank.allan.billscreen.model;

import com.nubank.allan.billscreen.model.Bill;
import com.nubank.allan.billscreen.model.LineItem;
import com.nubank.allan.billscreen.model.Summary;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by doisl_000 on 1/16/2016.
 */
public class BillTest extends TestCase {

    public void testConstructor_returnsNewObject_whenCalled(){
        Summary summary = new Summary();
        String state = "teststate";
        String id = "0x14";
        String barCode = "01010101";
        String digitableLine = "this is a line";
        HashMap<String, String> links = new HashMap<>();
        ArrayList<LineItem> items = new ArrayList<>();
        links.put("link1", "href1");
        links.put("link2", "href2");
        items.add(new LineItem());

        Bill bill = new Bill(summary, state, id, barCode, digitableLine, links, items);

        Assert.assertEquals("teststate", bill.getState());
        Assert.assertEquals("0x14", bill.getId());
        Assert.assertEquals("01010101", bill.getBarCode());
        Assert.assertEquals("this is a line", bill.getDigitableLine());
        Assert.assertEquals("href1", bill.getLinks().get("link1"));
        Assert.assertEquals("href2", bill.getLinks().get("link2"));
    }

    public void testSetSummary_setsSummary_whenCalled(){
        Date dueDate = new Date(2015,12,12);
        Date closeDate = new Date(2015,12,30);
        double pastBalance = 123.0;
        double totalBalance = 456.0;
        double interest = 789.0;
        double totalCumulative = 12.0;
        double paid = 135.0;
        double minPayment = 567.0;
        Date openDate = new Date(2015,12,1);
        Summary summary = new Summary(dueDate, closeDate, pastBalance, totalBalance, interest, totalCumulative, paid, minPayment, openDate);

        Bill bill = new Bill();
        bill.setSummary(summary);

        Assert.assertEquals(new Date(2015, 12, 12), bill.getSummary().getDueDate());
        Assert.assertEquals(new Date(2015, 12, 30), bill.getSummary().getCloseDate());
        Assert.assertEquals(123.0, bill.getSummary().getPastBalance());
        Assert.assertEquals(456.0, bill.getSummary().getTotalBalance());
        Assert.assertEquals(789.0, bill.getSummary().getInterest());
        Assert.assertEquals(12.0, bill.getSummary().getTotalCumulative());
        Assert.assertEquals(135.0, bill.getSummary().getPaid());
        Assert.assertEquals(567.0, bill.getSummary().getMinPayment());
        Assert.assertEquals(new Date(2015, 12, 1), bill.getSummary().getOpenDate());
    }

    public void testSetState_setsState_whenCalled(){
        String state = "this state";
        Bill bill = new Bill();
        bill.setState(state);

        Assert.assertEquals("this state", bill.getState());
    }

    public void testSetBarcode_setsBarcode_whenCalled(){
        String barcode = "abc123";
        Bill bill = new Bill();
        bill.setBarCode(barcode);

        Assert.assertEquals("abc123", bill.getBarCode());
    }

    public void testSetId_setsId_whenCalled(){
        String id = "newid123";
        Bill bill = new Bill();
        bill.setId(id);

        Assert.assertEquals("newid123", bill.getId());
    }

    public void testSetDigitableLine_setsDigitableLine_whenCalled(){
        String line = "12301230";
        Bill bill = new Bill();
        bill.setDigitableLine(line);

        Assert.assertEquals("12301230", bill.getDigitableLine());
    }

    public void testSetLinks_setsLinks_whenCalled(){
        HashMap<String, String> links = new HashMap<>();
        links.put("link1", "href1");
        links.put("link2", "href2");
        Bill bill = new Bill();

        bill.setLinks(links);

        Assert.assertEquals("href1", bill.getLinks().get("link1"));
        Assert.assertEquals("href2", bill.getLinks().get("link2"));
    }

    public void testSetItem_setsItems_whenCalled(){

        Date postDate = new Date(2016,12,12);
        String title = "linha";
        String href = "http://linha.com";
        int index = 1;
        int charges = 2;
        double amount = 220.0;

        LineItem item = new LineItem(postDate, title, href, index, charges, amount);
        ArrayList<LineItem> items = new ArrayList<>();
        items.add(item);

        Bill bill = new Bill();
        bill.setItems(items);

        Assert.assertEquals(new Date(2016, 12, 12), bill.getItems().get(0).getPostDate());
        Assert.assertEquals("linha", bill.getItems().get(0).getTitle());
        Assert.assertEquals("http://linha.com", bill.getItems().get(0).getHref());
        Assert.assertEquals(1, bill.getItems().get(0).getIndex());
        Assert.assertEquals(2, bill.getItems().get(0).getCharges());
        Assert.assertEquals(220.0, bill.getItems().get(0).getAmount());
    }
}
