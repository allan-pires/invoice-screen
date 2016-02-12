package com.nubank.allan.billscreen.factory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.nubank.allan.billscreen.controller.adapter.ViewPagerAdapter;

import org.mockito.Mockito;

/**
 * Created by doisl_000 on 2/11/2016.
 */
public class ViewPagerAdapterFactory {

    public static ViewPagerAdapter createViewPagerAdapterObject(){
        FragmentManager fm = Mockito.mock(FragmentManager.class);
        ViewPagerAdapter adapter = new ViewPagerAdapter(fm);

        adapter.addFragment(new Fragment(), "frag1");
        adapter.addFragment(new Fragment(), "frag2");
        adapter.addFragment(new Fragment(), "frag3");
        adapter.addFragment(new Fragment(), "frag4");
        adapter.addFragment(new Fragment(), "frag5");

        return adapter;
    }
}
