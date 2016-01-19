package com.nubank.allan.billscreen.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nubank.allan.billscreen.R;
import com.nubank.allan.billscreen.controller.ExceptionHandler;
import com.nubank.allan.billscreen.controller.HTTPConnectionHandler;
import com.nubank.allan.billscreen.controller.JSONHandler;
import com.nubank.allan.billscreen.model.Bill;
import com.nubank.allan.billscreen.view.fragment.MonthFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HTTPConnectionHandler connectionHandler = new HTTPConnectionHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets up the ViewPager (swipe through pages)
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        // Sets up the tabs using the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Adds fragments to the ViewPager
    private void setupViewPager(ViewPager vp){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        HTTPConnectionHandler httpHandler = new HTTPConnectionHandler(this);
        ExceptionHandler exceptionHandler = new ExceptionHandler(this);
        JSONHandler jsonHandler = new JSONHandler(this);
        JSONArray jsonArray = httpHandler.getJSONArrayData();

        try {
            if (jsonArray != null){
                int size = jsonArray.length();
                for(int i = 0; i < size; i++){
                    Bill bill = jsonHandler.parseJSONObjectToBill((JSONObject) jsonArray.get(i));
                    Bundle bundle = new Bundle();

                    bundle.putString("jsonObject", jsonArray.get(i).toString());
                    MonthFragment fragment = MonthFragment.newInstance(bundle);

                    adapter.addFragment(fragment, bill.getSummary().getDueMonth());
                }
            }
        }
        catch (JSONException e) {
            exceptionHandler.showErrorActivity(this, "err_json");
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
