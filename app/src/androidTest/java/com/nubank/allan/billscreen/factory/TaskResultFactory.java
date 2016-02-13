package com.nubank.allan.billscreen.factory;

import com.nubank.allan.billscreen.model.TaskResult;

import org.json.JSONException;

/**
 * Created by doisl_000 on 2/13/2016.
 */
public class TaskResultFactory {

    public static TaskResult createRealSucessTaskResultObject() throws JSONException {
        return new TaskResult(BillJSONArrayFactory.createBillJSONArray().toString(), 200);
    }

    public static TaskResult createSucessTaskResultObject(){
        return new TaskResult("ok", 200);
    }

    public static TaskResult create4xxTaskResultObject(){
        return new TaskResult("error", 404);
    }

    public static TaskResult create5xxTaskResultObject(){
        return new TaskResult("error", 503);
    }

    public static TaskResult createErrorTaskResultObject(){
        return new TaskResult("error", 30923);
    }
}
