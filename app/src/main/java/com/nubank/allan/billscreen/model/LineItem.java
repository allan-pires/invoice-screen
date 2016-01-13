package com.nubank.allan.billscreen.model;

import java.util.Date;

/**
 * Created by Allan on 12/01/2016.
 */
public class LineItem {
    private Date postDate;
    private String title;
    private String href;
    private int index;
    private int charges;
    private int amount;

    public LineItem() {
    }

    public LineItem(Date postDate, String title, String href, int index, int charges, int amount) {
        this.postDate = postDate;
        this.title = title;
        this.href = href;
        this.index = index;
        this.charges = charges;
        this.amount = amount;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
