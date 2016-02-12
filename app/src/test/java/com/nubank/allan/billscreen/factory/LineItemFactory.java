package com.nubank.allan.billscreen.factory;

import com.nubank.allan.billscreen.model.LineItem;

import java.util.Date;

/**
 * Created by doisl_000 on 2/11/2016.
 */
public class LineItemFactory {

    /*  Creates a new LineItem Object as follow:
            PostDate = 12/12/2016
            Title = "linha"
            Href = "http://linha.com"
            Index = 1
            Charges = 2
            Amount = 220.0
    */
    public static LineItem createLineItemObject(){
        return new LineItem(new Date(2016, 11, 12), "linha", "http://linha.com", 1, 2, 220.0);
    }
}
