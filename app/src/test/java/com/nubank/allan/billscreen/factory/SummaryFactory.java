package com.nubank.allan.billscreen.factory;

import com.nubank.allan.billscreen.model.Summary;

import java.util.Date;

/**
 * Created by doisl_000 on 2/11/2016.
 */
public class SummaryFactory {

    /*  Creates a new Summary Object as follow:
            DueDate = 12/12/2015
            CloseDate = 30/12/2015
            PastBalance = 123.0
            TotalBalance = 456.0
            Interest = 789.0
            TotalCumulative = 12.0
            Paid = 135.0
            MinPayment = 567.0
            OpenDate = 01/12/2015
    */
    public static Summary createSummaryObject(){
        return new Summary(new Date(2015,12,12), new Date(2015,12,30), 123.0, 456.0, 789.0, 12.0, 135.0, 567.0, new Date(2015,12,1));
    }
}
