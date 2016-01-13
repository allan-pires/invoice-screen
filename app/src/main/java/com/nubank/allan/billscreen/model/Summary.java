package com.nubank.allan.billscreen.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Allan on 12/01/2016.
 */
public class Summary {

    private Date dueDate;
    private Date closeDate;
    private int pastBalance;
    private int totalBalance;
    private int interest;
    private int totalCumulative;
    private int paid;
    private int minPayment;
    private Date openDate;

    public Summary() {
    }

    public Summary(Date dueDate, Date closeDate, int pastBalance, int totalBalance, int interest, int totalCumulative, int paid, int minPayment, Date openDate) {
        this.dueDate = dueDate;
        this.closeDate = closeDate;
        this.pastBalance = pastBalance;
        this.totalBalance = totalBalance;
        this.interest = interest;
        this.totalCumulative = totalCumulative;
        this.paid = paid;
        this.minPayment = minPayment;
        this.openDate = openDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getDueMonth(){
        return (new SimpleDateFormat("MMM").format(dueDate.getTime())).toString();
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public int getPastBalance() {
        return pastBalance;
    }

    public void setPastBalance(int pastBalance) {
        this.pastBalance = pastBalance;
    }

    public int getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public int getTotalCumulative() { return totalCumulative; }

    public void setTotalCumulative(int totalCumulative) { this.totalCumulative = totalCumulative; }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getMinPayment() {
        return minPayment;
    }

    public void setMinPayment(int minPayment) {
        this.minPayment = minPayment;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }
}
