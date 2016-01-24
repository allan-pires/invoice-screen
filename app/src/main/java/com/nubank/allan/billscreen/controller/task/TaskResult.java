package com.nubank.allan.billscreen.controller.task;

/**
 * Created by doisl_000 on 1/24/2016.
 */
// Result class for async task, returns the data and the code of the transaction
public class TaskResult {
    private String result;
    private int code;

    public TaskResult(String result, int code) {
        this.result = result;
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }
}
