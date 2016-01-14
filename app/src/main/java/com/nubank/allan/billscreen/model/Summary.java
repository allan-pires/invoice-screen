package com.nubank.allan.billscreen.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Allan on 12/01/2016.
 */
public class Summary {

    private Date dueDate;
    private Date closeDate;
    private double pastBalance;
    private double totalBalance;
    private double interest;
    private double totalCumulative;
    private double paid;
    private double minPayment;
    private Date openDate;

    public Summary() {
    }

    public Summary(Date dueDate, Date closeDate, double pastBalance, double totalBalance, double interest, double totalCumulative, double paid, double minPayment, Date openDate) {
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

    public String getDueDayMonth(){
        return (new SimpleDateFormat("dd MMM").format(dueDate.getTime())).toString();
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

    public double getPastBalance() {
        return pastBalance;
    }

    public void setPastBalance(double pastBalance) {
        this.pastBalance = pastBalance;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getTotalCumulative() { return totalCumulative; }

    public void setTotalCumulative(double totalCumulative) { this.totalCumulative = totalCumulative; }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getMinPayment() {
        return minPayment;
    }

    public void setMinPayment(double minPayment) {
        this.minPayment = minPayment;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }
}
