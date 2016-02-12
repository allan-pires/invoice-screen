package com.nubank.allan.billscreen.controller.handler;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.test.InstrumentationTestCase;

import com.nubank.allan.billscreen.R;
import com.nubank.allan.billscreen.controller.handler.ExceptionHandler;
import com.nubank.allan.billscreen.view.ErrorActivity;
import com.nubank.allan.billscreen.view.MainActivity;

import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.any;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.notNull;

/**
 * Created by doisl_000 on 1/21/2016.
 */
public class ExceptionHandlerTest extends InstrumentationTestCase {

    private Instrumentation.ActivityMonitor monitor;
    private Intent intent;
    private Context context;
    private MainActivity activity;
    private Resources resources;
    private ExceptionHandler ex;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty(
                "dexmaker.dexcache",
                getInstrumentation().getTargetContext().getCacheDir().getPath());

        monitor = new Instrumentation.ActivityMonitor(ErrorActivity.class.getName(), null, false);
        getInstrumentation().addMonitor(monitor);

        intent = Mockito.mock(Intent.class);
        context = Mockito.mock(Context.class);
        activity = Mockito.mock(MainActivity.class);
        resources = Mockito.mock(Resources.class);
        ex = Mockito.mock(ExceptionHandler.class);
    }

    public void testGetMessageFromCode_returnsCode_whenCodeNoInternet(){
        ExceptionHandler exe = new ExceptionHandler();
        String message = "Parece que você está sem internet! Por favor, verifique a sua conexão e tente novamente.";
        Mockito.when(context.getResources()).thenReturn(resources);
        Mockito.when(resources.getString(anyInt())).thenReturn(message);
        Mockito.when(exe.getMessageFromCode((Context) notNull(), anyString())).thenReturn("Houve algum erro com o seu pedido.");

        String returned = exe.getMessageFromCode(context, "N");

        assertEquals(message, returned);
    }

    public void testShowErrorActivity_showsErrorActivity_whenCalled(){
        Mockito.when(context.getResources()).thenReturn(resources);
        Mockito.when(ex.getMessageFromCode((Context)notNull(), (String)notNull())).thenReturn("Houve algum erro com o seu pedido.");
        Mockito.doNothing().when(activity).startActivity(intent);
        Mockito.doNothing().when(activity).finish();

        ex.showErrorActivity(context, "404");
        assertEquals("Houve algum erro com o seu pedido.", ex.getMessageFromCode(context, "404"));
    }
}
