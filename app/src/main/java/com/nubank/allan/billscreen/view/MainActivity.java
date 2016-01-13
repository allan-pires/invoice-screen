package com.nubank.allan.billscreen.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.nubank.allan.billscreen.R;
import com.nubank.allan.billscreen.controller.JSONHandler;
import com.nubank.allan.billscreen.model.Bill;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Bill> billArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String fileContent;
        try {
            billArray = JSONHandler.getBillsFromUrl();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Sets up the ViewPager (swipe through pages)
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, billArray);

        // Sets up the tabs using the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Adds fragments to the ViewPager
    private void setupViewPager(ViewPager vp, ArrayList<Bill> billArray){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        int size = billArray.size();
        for(int i = 0; i < size; i++){
            adapter.addFragment(new MonthFragment(), billArray.get(i).getSummary().getDueMonth());
        }

        vp.setAdapter(adapter);
    }

    // ViewPager adapter
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();
        private List<String> fragmentListTitle = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            fragmentListTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return fragmentListTitle.get(position);
        }
    }
}
