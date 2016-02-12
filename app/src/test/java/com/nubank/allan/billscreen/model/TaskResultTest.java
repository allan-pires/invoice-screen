package com.nubank.allan.billscreen.model;

import com.nubank.allan.billscreen.factory.TaskResultFactory;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by doisl_000 on 2/5/2016.
 */
public class TaskResultTest extends TestCase {

    public void testConstructor_returnsNewObject_whenCalled(){
        TaskResult tr = TaskResultFactory.createSucessTaskResultObject();

        Assert.assertNotNull(tr);
    }

    public void testGetResult_returnsResult_whenCalled(){
        TaskResult tr = TaskResultFactory.createSucessTaskResultObject();

        assertEquals("ok", tr.getResult());
    }

    public void testGetCode_returnsCode_whenCalled(){
        TaskResult tr = TaskResultFactory.createSucessTaskResultObject();

        assertEquals(200, tr.getCode());
    }
}
