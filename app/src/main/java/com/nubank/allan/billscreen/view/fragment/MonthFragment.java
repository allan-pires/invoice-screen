package com.nubank.allan.billscreen.view.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nubank.allan.billscreen.R;
import com.nubank.allan.billscreen.controller.JSONHandler;
import com.nubank.allan.billscreen.model.Bill;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;

/**
 * Created by Allan on 13/01/2016.
 */
public class MonthFragment extends Fragment {

    private Bill bill;
    private TextView totalAmount;
    private TextView dueDate;

    // Static constructor
    public static MonthFragment newInstance(Bundle b) {
        MonthFragment fragment = new MonthFragment();
        fragment.setArguments(b);

        return fragment;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        totalAmount = (TextView) view.findViewById(R.id.totalAmount);
        dueDate = (TextView) view.findViewById(R.id.dueDate);

        if (getArguments() != null){
            try {
                JSONObject obj = new JSONObject(getArguments().getString("jsonObject"));
                bill = JSONHandler.parseJSONObjectToBill(obj);

                int total = bill.getSummary().getTotalBalance();
                totalAmount.setText("R$ "+ String.format("%,d", total));
                dueDate.setText("Vencimento "+(bill.getSummary().getDueDayMonth()));

                String state = bill.getState();

                switch (state){
                    case "overdue" :
                        view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(126,211,33));
                        break;
                    case "closed" :
                        view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(229,97,92));
                        break;
                    case "open" :
                        view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(64,170,185));
                        break;
                    case "future" : view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(245,166,35));
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}
