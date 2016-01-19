package com.nubank.allan.billscreen.view.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nubank.allan.billscreen.R;
import com.nubank.allan.billscreen.controller.JSONHandler;
import com.nubank.allan.billscreen.model.Bill;
import com.nubank.allan.billscreen.model.LineItem;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Allan on 13/01/2016.
 */
public class MonthFragment extends Fragment {

    private Bill bill;

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


        if (getArguments() != null){
            try {
                JSONObject obj = new JSONObject(getArguments().getString("jsonObject"));
                JSONHandler jsonHandler = new JSONHandler(this.getActivity());
                bill = jsonHandler.parseJSONObjectToBill(obj);
                setLayout(view, bill);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    private void setLayout(View view, Bill bill){

        ((TextView) view.findViewById(R.id.totalAmount)).setText(DecimalFormat.getCurrencyInstance().format(bill.getSummary().getTotalBalance()));
        ((TextView) view.findViewById(R.id.dueDate)).setText("Vencimento " + (bill.getSummary().getDueDayMonth()));

        String state = bill.getState();

        switch (state){
            case "overdue" :
                setOverdueLayout(view, bill);
                break;
            case "closed" :
                setClosedLayout(view, bill);
                break;
            case "open" :
                setOpenLayout(view, bill);
                break;
            case "future" :
                setFutureLayout(view, bill);
                break;
        }

        int size = bill.getItems().size();
        TableLayout table = (TableLayout) view.findViewById(R.id.LineItems);
        for(int i = 0; i < size; i++){
            LineItem item = bill.getItems().get(i);
            addLineItem(table, item.getPostDateDayMonth(), item.getTitle(), item.getAmount());
        }
    }

    private void setOverdueLayout(View view, Bill bill) {
        view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(126, 211, 33));
        LinearLayout headerDetails = (LinearLayout) view.findViewById(R.id.HeaderDetails);

        TextView recieved_value = new TextView(headerDetails.getContext());
        recieved_value.setText("PAGAMENTO RECEBIDO\n" + DecimalFormat.getCurrencyInstance().format(bill.getSummary().getPaid()));

        headerDetails.addView(recieved_value);
    }

    private void setClosedLayout(View view, Bill bill) {
        double total = bill.getSummary().getTotalBalance();
        double temp = bill.getSummary().getTotalCumulative();
        view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(229, 97, 92));
        LinearLayout headerDetails = (LinearLayout) view.findViewById(R.id.HeaderDetails);

        TextView monthly_expenses = new TextView(headerDetails.getContext());
        monthly_expenses.setText("Gastos do mês " + DecimalFormat.getCurrencyInstance().format(temp));

        TextView not_paid = new TextView(headerDetails.getContext());
        not_paid.setText("Valores não pagos " + DecimalFormat.getCurrencyInstance().format(total - temp));

        TextView interest = new TextView(headerDetails.getContext());
        double interest_double = bill.getSummary().getInterest();
        interest.setText("Juros "+ bill.getSummary().getInterest() + DecimalFormat.getCurrencyInstance().format(temp * interest_double));

        Button generate_billet = new Button(headerDetails.getContext());
        generate_billet.setText("GERAR BOLETO");

        headerDetails.addView(monthly_expenses);
        headerDetails.addView(not_paid);
        headerDetails.addView(interest);
        headerDetails.addView(generate_billet);
    }

    private void setOpenLayout(View view, Bill bill) {
        view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(64,170,185));
        ((TextView) view.findViewById(R.id.closeDate)).setText("Fechamento em " + (bill.getSummary().getDueDayMonth()));

        LinearLayout headerDetails = (LinearLayout) view.findViewById(R.id.HeaderDetails);

        Button generate_billet = new Button(headerDetails.getContext());
        generate_billet.setText("GERAR BOLETO");
        headerDetails.addView(generate_billet);
    }

    private void setFutureLayout(View view, Bill bill) {
        view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(245, 166, 35));
        ((TextView) view.findViewById(R.id.dueDate)).setText("FATURA PARCIAL");

        view.findViewById(R.id.HeaderDetails).setVisibility(View.GONE);
    }

    private void addLineItem(TableLayout table, String date, String place, double value){
            final TableRow tr = (TableRow) getLayoutInflater(new Bundle()).inflate(R.layout.line_item, null);

            TextView text;

            // Fill out our cells
            text = (TextView) tr.findViewById(R.id.itemDate);
            text.setText(date);

            text = (TextView) tr.findViewById(R.id.itemPlace);
            text.setText(place);

            text = (TextView) tr.findViewById(R.id.itemValue);
            text.setText(String.format("%10.2f", Math.abs(value)));
            table.addView(tr);
    }
}
