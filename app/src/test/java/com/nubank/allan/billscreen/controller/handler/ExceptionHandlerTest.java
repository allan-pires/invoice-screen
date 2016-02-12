package com.nubank.allan.billscreen.controller.handler;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import com.nubank.allan.billscreen.view.ErrorActivity;
import com.nubank.allan.billscreen.view.MainActivity;

import junit.framework.TestCase;

import org.mockito.Mockito;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.notNull;

/**
 * Created by doisl_000 on 1/21/2016.
 */

public class ExceptionHandlerTest extends TestCase {

    private Intent intent;
    private Context context;
    private MainActivity activity;
    private Resources resources;
    private ExceptionHandler ex;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        intent = Mockito.mock(Intent.class);
        context = Mockito.mock(Context.class);
        activity = Mockito.mock(MainActivity.class);
        resources = Mockito.mock(Resources.class);
        ex = new ExceptionHandler();
    }

    public void testGetMessageFromCode_returnsCodeNMessage_whenCodeN(){
        String message = "Parece que você está sem internet! Por favor, verifique a sua conexão e tente novamente.";

        Mockito.when(context.getResources()).thenReturn(resources);
        Mockito.when(resources.getString(anyInt())).thenReturn(message);

        String returned = ex.getMessageFromCode(context, "N");

        assertEquals(message, returned);
    }

    public void testGetMessageFromCode_returnsCode4xxMessage_whenCode4xx(){
        String message = "Houve algum erro com o seu pedido.";

        Mockito.when(context.getResources()).thenReturn(resources);
        Mockito.when(resources.getString(anyInt())).thenReturn(message);

        String returned = ex.getMessageFromCode(context, "404");

        assertEquals(message, returned);
    }

    public void testGetMessageFromCode_returnsCode5xxMessage_whenCode5xx(){
        String message = "Desculpe, estamos enfrentando problemas técnicos. Por favor, tente novamente mais tarde";

        Mockito.when(context.getResources()).thenReturn(resources);
        Mockito.when(resources.getString(anyInt())).thenReturn(message);

        String returned = ex.getMessageFromCode(context, "503");

        assertEquals(message, returned);
    }

    public void testGetMessageFromCode_returnsDefaultMessage_whenOtherCode(){
        String message = "Desculpe, ocorreu algum problema. Por favor, tente novamente mais tarde.";

        Mockito.when(context.getResources()).thenReturn(resources);
        Mockito.when(resources.getString(anyInt())).thenReturn(message);

        String returned = ex.getMessageFromCode(context, "anything_err_xx_2123fd");

        assertEquals(message, returned);
    }

    public void testShowErrorActivity_showsErrorActivity_whenCalled(){
        Mockito.when(context.getResources()).thenReturn(resources);
        Mockito.doNothing().when(activity).startActivity(intent);
        Mockito.doNothing().when(activity).finish();

        ex.showErrorActivity(context, "404");
    }
}
