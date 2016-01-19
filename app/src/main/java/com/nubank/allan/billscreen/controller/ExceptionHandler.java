package com.nubank.allan.billscreen.controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.nubank.allan.billscreen.view.ErrorActivity;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by doisl_000 on 1/17/2016.
 */
public class ExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {

    private Activity context = null;

    public ExceptionHandler(Activity context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Intent intent = new Intent(context, ErrorActivity.class);
        intent.putExtra("error_message", ex.getMessage());
        context.startActivity(intent);
    }

    public static void showErrorActivity(Context context, String message){
        Intent intent = new Intent(context, ErrorActivity.class);
        intent.putExtra("error_message", message);
        context.startActivity(intent);
    }
}
