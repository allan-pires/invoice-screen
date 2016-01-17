package com.nubank.allan.billscreen.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.nubank.allan.billscreen.R;
import com.nubank.allan.billscreen.controller.JSONHandler;
import com.nubank.allan.billscreen.controller.RESTHandler;
import com.nubank.allan.billscreen.model.Bill;
import com.nubank.allan.billscreen.view.fragment.MonthFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isConnectedToInternet()) {
            // Sets up the ViewPager (swipe through pages)
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            try {
                setupViewPager(viewPager);

                // Sets up the tabs using the ViewPager
                tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                tabLayout.setupWithViewPager(viewPager);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            new AlertDialog.Builder(this)
                    .setMessage(R.string.no_connection)
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    // Adds fragments to the ViewPager
    private void setupViewPager(ViewPager vp) throws ExecutionException, InterruptedException, JSONException, ParseException {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        RESTHandler asyncTask = new RESTHandler();
        JSONArray jsonArray = asyncTask.execute().get();

        int size = jsonArray.length();
        for(int i = 0; i < size; i++){
            Bill bill = JSONHandler.parseJSONObjectToBill((JSONObject) jsonArray.get(i));
            Bundle bundle = new Bundle();
            bundle.putString("jsonObject", jsonArray.get(i).toString());
            MonthFragment fragment = MonthFragment.newInstance(bundle);

            adapter.addFragment(fragment, bill.getSummary().getDueMonth());
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
