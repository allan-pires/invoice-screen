package com.nubank.allan.billscreen.controller.handler;

import android.content.Context;
import android.content.Intent;

import com.nubank.allan.billscreen.R;
import com.nubank.allan.billscreen.view.ErrorActivity;

/**
 * Created by doisl_000 on 1/17/2016.
 */
public class ExceptionHandler {

    // Shows ErrorActivity and display the corresponding error message
    public void showErrorActivity(Context context, String code){
        String extra = getMessageFromCode(context, code);
        Intent intent = new Intent(context, ErrorActivity.class);
        intent.putExtra("error_code", code);
        intent.putExtra("error_message", extra);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // Returns error messages by code
    public String getMessageFromCode(Context context, String code){
        switch (code.charAt(0)){
            case 'N': return context.getResources().getString(R.string.err_no_connection);
            case '4': return context.getResources().getString(R.string.err_4xx);
            case '5': return context.getResources().getString(R.string.err_5xx);
            default: return context.getResources().getString(R.string.err_default_message);
        }
    }
}
