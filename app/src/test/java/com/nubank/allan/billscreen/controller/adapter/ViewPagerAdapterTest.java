package com.nubank.allan.billscreen.controller.adapter;


import com.nubank.allan.billscreen.factory.ViewPagerAdapterFactory;

import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * Created by doisl_000 on 2/4/2016.
 */
public class ViewPagerAdapterTest extends TestCase{

    public void testAddFragment_addsFragment_whenCalled(){

        ViewPagerAdapter adapter = ViewPagerAdapterFactory.createViewPagerAdapterObject();

        Assert.assertEquals(5, adapter.getCount());

        for (int i = 0; i < 5; i++){
            Assert.assertNotNull(adapter.getItem(i));
        }

        Assert.assertEquals("frag1", adapter.getPageTitle(0));
        Assert.assertEquals("frag2", adapter.getPageTitle(1));
        Assert.assertEquals("frag3", adapter.getPageTitle(2));
        Assert.assertEquals("frag4", adapter.getPageTitle(3));
        Assert.assertEquals("frag5", adapter.getPageTitle(4));
    }
}
