package com.nubank.allan.billscreen.controller;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.nubank.allan.billscreen.view.ErrorActivity;
import com.nubank.allan.billscreen.view.MainActivity;

import junit.framework.TestCase;

import org.mockito.Mockito;

/**
 * Created by doisl_000 on 1/21/2016.
 */
public class ExceptionHandlerTest extends InstrumentationTestCase {

    private Instrumentation.ActivityMonitor monitor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty(
                "dexmaker.dexcache",
                getInstrumentation().getTargetContext().getCacheDir().getPath());

        monitor = new Instrumentation.ActivityMonitor(ErrorActivity.class.getName(), null, false);
        getInstrumentation().addMonitor(monitor);
    }

    public void testShowErrorActivity_showsErrorActivity_whenCalled(){
        Intent intent = Mockito.mock(Intent.class);
        Context context = Mockito.mock(Context.class);
        MainActivity activity = Mockito.mock(MainActivity.class);
        ExceptionHandler ex = new ExceptionHandler(activity);

        Mockito.when(context.getApplicationContext()).thenReturn(context);
        Mockito.doNothing().when(activity).startActivity(intent);
        Mockito.doNothing().when(activity).finish();

        ex.showErrorActivity(context, "404");
        assertEquals("Houve algum erro com o seu pedido.", ex.getMessageFromCode("404"));
    }
}
