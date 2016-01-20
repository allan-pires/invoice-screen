package com.nubank.allan.billscreen.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.nubank.allan.billscreen.R;
import com.nubank.allan.billscreen.controller.ExceptionHandler;
import com.nubank.allan.billscreen.controller.HTTPConnectionHandler;
import com.nubank.allan.billscreen.controller.JSONHandler;
import com.nubank.allan.billscreen.controller.ViewPagerAdapter;
import com.nubank.allan.billscreen.model.Bill;
import com.nubank.allan.billscreen.view.fragment.MonthFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    private TabLayout tabs;
    private ViewPager viewPager;
    private ArrayList<Bill> bills = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets up the ViewPager (swipe through pages)
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // Sets up the Tabs
        tabs = (TabLayout) findViewById(R.id.tab_layout);

        setupTabsLayout(tabs);
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
                    bundle.putInt("count", i);
                    MonthFragment fragment = MonthFragment.newInstance(bundle);

                    Date due_month = bill.getSummary().getDueDate();
                    adapter.addFragment(fragment, bill.getSummary().getMonthText(due_month));
                    bills.add(bill);
                }
            }
        }
        catch (JSONException e) {
            exceptionHandler.showErrorActivity(this, "err_json");
        }

        vp.setAdapter(adapter);
    }

    // Customize each tab
    private void setupTabsLayout(TabLayout tabs){
        tabs.setupWithViewPager(viewPager);
        tabs.setSelectedTabIndicatorColor(Color.TRANSPARENT);

        // For each tab
        for (int i = 0; i < tabs.getTabCount(); i++){
            TabLayout.Tab tab = tabs.getTabAt(i);

            if (tab != null){
                // Get the corresponding bill
                Bill bill = bills.get(i);
                String state = bill.getState();
                Date date = bill.getSummary().getOpenDate();

                // Inflate the custom view
                View view = getLayoutInflater().inflate(R.layout.custom_view_tab, null);
                TextView tab_title = (TextView) view.findViewById(R.id.tabTitle);
                View tab_selector = view.findViewById(R.id.tabSelector);
                tab_title.setText(bill.getSummary().getMonthText(date));

                // Set the corresponding color
                switch (state){
                    case "overdue":
                        tab_title.setTextColor(getResources().getColor(R.color.softgreen));
                        tab_selector.setBackground(getDrawable(R.drawable.overdue_tab_selector));
                        break;
                    case "closed":
                        tab_title.setTextColor(getResources().getColor(R.color.softred));
                        tab_selector.setBackground(getDrawable(R.drawable.closed_tab_selector));
                        break;
                    case "open":
                        tab_title.setTextColor(getResources().getColor(R.color.softblue));
                        tab_selector.setBackground(getDrawable(R.drawable.open_tab_selector));
                        break;
                    case "future":
                        tab_title.setTextColor(getResources().getColor(R.color.softorange));
                        tab_selector.setBackground(getDrawable(R.drawable.future_tab_selector));
                        break;
                }

                // Set the selector visible and the text larger if tab is selected
                if (tab.isSelected()){
                    tab_title.setTextSize(20);
                    tab_selector.setVisibility(View.VISIBLE);
                }

                // Adds the custom view to the tab
                tab.setCustomView(view);
            }
        }

        // Adds a OnTabListener to the TabLayout to change styles
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            // Set the selector visible and the text larger if tab is selected
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView tab_title = (TextView) view.findViewById(R.id.tabTitle);
                tab_title.setTextSize(20);
                tab_title.setGravity(View.TEXT_ALIGNMENT_CENTER);
                view.findViewById(R.id.tabSelector).setVisibility(View.VISIBLE);
                tab.setCustomView(view);
                viewPager.setCurrentItem(tab.getPosition());
            }

            // Set the selector gone and the text smaller if tab is unselected
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView tab_title = (TextView) view.findViewById(R.id.tabTitle);
                tab_title.setTextSize(15);
                view.findViewById(R.id.tabSelector).setVisibility(View.GONE);
                tab.setCustomView(view);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
