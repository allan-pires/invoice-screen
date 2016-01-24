package com.nubank.allan.billscreen.model;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by doisl_000 on 1/16/2016.
 */
public class LineItemTest extends TestCase{

    public void testConstructor_returnsNewObject_whenCalled(){
        Date postDate = new Date(2016,12,12);
        String title = "linha";
        String href = "http://linha.com";
        int index = 1;
        int charges = 2;
        double amount = 220.0;

        LineItem item = new LineItem(postDate, title, href, index, charges, amount);

        Assert.assertEquals(new Date(2016,12,12), item.getPostDate());
        Assert.assertEquals("linha", item.getTitle());
        Assert.assertEquals("http://linha.com", item.getHref());
        Assert.assertEquals(1, item.getIndex());
        Assert.assertEquals(2, item.getCharges());
        Assert.assertEquals(220.0, item.getAmount());
    }

    public void testSetPostDate_setsPostDate_whenCalled(){
        Date postDate = new Date(2016,12,12);

        LineItem item = new LineItem();
        item.setPostDate(postDate);

        Assert.assertEquals(new Date(2016, 12, 12), item.getPostDate());
    }

    public void testSetTitle_setsTitle_whenCalled(){
        String title = "linha";

        LineItem item = new LineItem();
        item.setTitle(title);

        Assert.assertEquals("linha", item.getTitle());
    }

    public void testSetHref_setsHref_whenCalled(){
        String href = "http://linha.com";

        LineItem item = new LineItem();
        item.setHref(href);

        Assert.assertEquals("http://linha.com", item.getHref());
    }

    public void testSetIndex_setsIndex_whenCalled(){
        int index = 1;

        LineItem item = new LineItem();
        item.setIndex(index);

        Assert.assertEquals(1, item.getIndex());
    }

    public void testSetCharges_setsCharges_whenCalled(){
        int charges = 2;

        LineItem item = new LineItem();
        item.setCharges(charges);

        Assert.assertEquals(2, item.getCharges());
    }

    public void testSetAmount_setsAmount_whenCalled(){
        double amount = 220.0;

        LineItem item = new LineItem();
        item.setAmount(amount);

        Assert.assertEquals(220.0, item.getAmount());
    }

}
