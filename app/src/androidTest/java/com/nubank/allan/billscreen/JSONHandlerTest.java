package com.nubank.allan.billscreen;

import com.nubank.allan.billscreen.controller.JSONHandler;
import junit.framework.*;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Allan on 12/01/2016.
 */
public class JSONHandlerTest extends TestCase{

    public static void testGetJSONFromUrl_returnsJSONObject_when_called() throws JSONException {
        JSONObject jobj;
        jobj = JSONHandler.getJSONFromUrl("http://jsonview.com/example.json");

        Assert.assertEquals("guy", jobj.get("hey").toString());
    }
}
