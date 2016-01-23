package com.nubank.allan.billscreen.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.nubank.allan.billscreen.view.ErrorActivity;

/**
 * Created by doisl_000 on 1/17/2016.
 */
public class ExceptionHandler {

    public Activity owner;

    // Constructor
    public ExceptionHandler(Activity owner) {
        this.owner = owner;
    }

    // Shows ErrorActivity and display the corresponding error message
    public void showErrorActivity(Context context, String code){
        String extra = getMessageFromCode(code);
        Intent intent = new Intent(context, ErrorActivity.class);
        intent.putExtra("error_code", code);
        intent.putExtra("error_message", extra);
        owner.startActivity(intent);
        owner.finish();
    }

    // Returns error messages by code
    public String getMessageFromCode(String code){
        switch (code.charAt(0)){
            case 'N': return "Parece que você está sem internet!\nPor favor, verifique a sua conexão e tente novamente.";
            case '4': return "Houve algum erro com o seu pedido.";
            case '5': return "Desculpe, estamos enfrentando problemas técnicos. Por favor, tente novamente mais tarde.";
            default: return "Desculpe, ocorreu algum problema durante a execução do aplicativo. Por favor, tente novamente mais tarde.";
        }
    }
}
