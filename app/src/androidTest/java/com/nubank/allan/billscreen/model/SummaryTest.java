package com.nubank.allan.billscreen.model;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by doisl_000 on 1/16/2016.
 */
public class SummaryTest extends TestCase {

    public void testConstructor_returnsNewObject_whenCalled(){
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

    public void testSetDueDate_setsDueDate_whenCalled(){
        Date dueDate = new Date(2015,12,12);

        Summary summary = new Summary();
        summary.setDueDate(dueDate);

        Assert.assertEquals(new Date(2015,12,12), summary.getDueDate());
    }

    public void testSetCloseDate_setsCloseDate_whenCalled(){
        Date closeDate = new Date(2015,12,30);

        Summary summary = new Summary();
        summary.setCloseDate(closeDate);

        Assert.assertEquals(new Date(2015,12,30), summary.getCloseDate());
    }

    public void testSetPastBalance_setsPastBalance_whenCalled(){
        double pastBalance = 123.0;

        Summary summary = new Summary();
        summary.setPastBalance(pastBalance);

        Assert.assertEquals(123.0, summary.getPastBalance());
    }

    public void testSetTotalBalance_setsTotalBalance_whenCalled(){
        double totalBalance = 456.0;

        Summary summary = new Summary();
        summary.setTotalBalance(totalBalance);

        Assert.assertEquals(456.0, summary.getTotalBalance());
    }

    public void testSetInterest_setsInterest_whenCalled(){
        double interest = 789.0;

        Summary summary = new Summary();
        summary.setInterest(interest);

        Assert.assertEquals(789.0, summary.getInterest());
    }

    public void testSetTotalCumulative_setsTotalCumulative_whenCalled(){
        double totalCumulative = 12.0;

        Summary summary = new Summary();
        summary.setTotalCumulative(totalCumulative);

        Assert.assertEquals(12.0, summary.getTotalCumulative());
    }

    public void testSetPaid_setsPaid_whenCalled(){
        double paid = 135.0;

        Summary summary = new Summary();
        summary.setPaid(paid);

        Assert.assertEquals(135.0, summary.getPaid());
    }

    public void testSetMinPayment_setsMinPayment_whenCalled(){
        double minPayment = 567.0;

        Summary summary = new Summary();
        summary.setMinPayment(minPayment);

        Assert.assertEquals(567.0, summary.getMinPayment());
    }

    public void testSetOpenDate_setsOpenDate_whenCalled(){
        Date openDate = new Date(2015,12,1);

        Summary summary = new Summary();
        summary.setOpenDate(openDate);

        Assert.assertEquals(new Date(2015,12,1), summary.getOpenDate());
    }
}
