package com.nubank.allan.billscreen.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nubank.allan.billscreen.R;
import com.nubank.allan.billscreen.controller.handler.JSONHandler;
import com.nubank.allan.billscreen.model.Bill;
import com.nubank.allan.billscreen.model.LineItem;
import com.nubank.allan.billscreen.model.Summary;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by Allan on 13/01/2016.
 */
public class MonthFragment extends Fragment{

    private Bill bill;
    private int count;

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
                count = getArguments().getInt("count");
                setLayout(view, bill);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    private void setLayout(View view, Bill bill){

        Date due_date = bill.getSummary().getDueDate();
        Date close_date = bill.getSummary().getCloseDate();
        Date open_date = bill.getSummary().getOpenDate();

        StringBuilder sb = new StringBuilder();
        sb.append(Summary.getDayAndMonthText(open_date));
        sb.append(" ATÉ ");
        sb.append(Summary.getDayAndMonthText(close_date));

        ((TextView) view.findViewById(R.id.DateRangeText)).setText(sb.toString());
        ((TextView) view.findViewById(R.id.totalAmount)).setText(DecimalFormat.getCurrencyInstance().format(bill.getSummary().getTotalBalance()));
        ((TextView) view.findViewById(R.id.dueDate)).setText("Vencimento " + (Summary.getDayAndMonthText(due_date)));

        String state = bill.getState();

        switch (state){
            case "overdue" :
                setupOverdueLayout(view, bill);
                break;
            case "closed" :
                setupClosedLayout(view, bill);
                break;
            case "open" :
                setupOpenLayout(view, bill);
                break;
            case "future" :
                setupFutureLayout(view, bill);
                break;
        }

        int size = bill.getItems().size();
        TableLayout table = (TableLayout) view.findViewById(R.id.LineItems);

        for(int i = size-1; i >= 0; i--){
            LineItem item = bill.getItems().get(i);
            addLineItem(table, item.getPostDateDayMonth(), item.getTitle(), item.getAmount(), item.getIndex(),item.getCharges());
        }
    }

    private void setupOverdueLayout(View view, Bill bill) {
        view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(126, 211, 33));

        // Show “PAGAMENTO RECEBIDO” if paid ​is negative.
        if (bill.getSummary().getPaid() < 0){
            LinearLayout overdueDetails = (LinearLayout) view.findViewById(R.id.OverdueDetails);
            overdueDetails.setVisibility(View.VISIBLE);
            TextView paidText = (TextView) view.findViewById(R.id.paidText);
            paidText.setText(DecimalFormat.getCurrencyInstance().format(bill.getSummary().getPaid()));
        }
        else{
            view.findViewById(R.id.HeaderDetails).setVisibility(View.GONE);
            view.findViewById(R.id.Separator).setVisibility(View.GONE);
        }

    }

    private void setupClosedLayout(View view, Bill bill) {
        double totalBalance = bill.getSummary().getTotalBalance();
        double totalCumulative = bill.getSummary().getTotalCumulative();
        double pastBalance = bill.getSummary().getPastBalance();
        double interest = bill.getSummary().getInterest();

        view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(229, 97, 92));
        LinearLayout headerDetails = (LinearLayout) view.findViewById(R.id.HeaderDetails);

        LinearLayout closedDetails = (LinearLayout) view.findViewById(R.id.ClosedDetails);
        closedDetails.setVisibility(View.VISIBLE);

        TextView temp;
        TableRow rowtemp;

        // “Gastos do mês” - total_cumulative (Only if it’s greater than 0)
        if (totalCumulative > 0){
            temp = (TextView) view.findViewById(R.id.monthlyExpensesText);
            temp.setText(DecimalFormat.getCurrencyInstance().format(totalCumulative));
            rowtemp = (TableRow) view.findViewById(R.id.monthlyExpensesRow);
            rowtemp.setVisibility(View.VISIBLE);
        }

        // “Valores não pagos” - past_balance (Only if it’s greater than 0)
        if (pastBalance > 0){
            temp = (TextView) view.findViewById(R.id.notPaidText);
            temp.setText(DecimalFormat.getCurrencyInstance().format(pastBalance));
            rowtemp = (TableRow) view.findViewById(R.id.notPaidRow);
            rowtemp.setVisibility(View.VISIBLE);
        }

        // “Valores pré-pago” - past_balance (Only if it’s less than 0)
        if (pastBalance < 0){
            temp = (TextView) view.findViewById(R.id.prePaidText);
            temp.setText(DecimalFormat.getCurrencyInstance().format(pastBalance));
            rowtemp = (TableRow) view.findViewById(R.id.prePaidRow);
            rowtemp.setVisibility(View.VISIBLE);
        }

        // “Juros 7,75%” - interest (Only if it’s greater than 0)
        if (interest > 0){
            temp = (TextView) view.findViewById(R.id.interestText);
            temp.setText(DecimalFormat.getCurrencyInstance().format(interest));
            rowtemp = (TableRow) view.findViewById(R.id.interestRow);
            rowtemp.setVisibility(View.VISIBLE);
        }
    }

    private void setupOpenLayout(View view, Bill bill) {
        view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(64, 170, 185));
        Date date = bill.getSummary().getCloseDate();
        ((TextView) view.findViewById(R.id.closeDate)).setText("Fechamento em " + Summary.getDayAndMonthText(date));

        LinearLayout openDetails = (LinearLayout) view.findViewById(R.id.OpenDetails);
        openDetails.setVisibility(View.VISIBLE);
    }

    private void setupFutureLayout(View view, Bill bill) {
        view.findViewById(R.id.HeaderLayout).setBackgroundColor(Color.rgb(245, 166, 35));
        ((TextView) view.findViewById(R.id.closeDate)).setText("FATURA PARCIAL");

        view.findViewById(R.id.HeaderDetails).setVisibility(View.GONE);
        view.findViewById(R.id.Separator).setVisibility(View.GONE);
    }

    private void addLineItem(TableLayout table, String date, String place, double value, int index, int charges){
            final TableRow tr = (TableRow) getLayoutInflater(new Bundle()).inflate(R.layout.line_item, null);

            TextView text;
            StringBuilder place_truncated = new StringBuilder();

            // Fill out our cells
            text = (TextView) tr.findViewById(R.id.itemDate);
            text.setText(date.toUpperCase());

            text = (TextView) tr.findViewById(R.id.itemPlace);

            if (place.length() > 28){
                place_truncated.append(place.substring(0, 30));
                place_truncated.append("...");
            }
            else{
                place_truncated.append(place);
            }

            if(charges > 1){
                place_truncated.append(" ");
                place_truncated.append(index+1);
                place_truncated.append("/");
                place_truncated.append(charges);
            }

            text.setText(place_truncated);

            text = (TextView) tr.findViewById(R.id.itemValue);
            text.setText(String.format("%10.2f", value));
            if (value < 0){
                text.setTextColor(getResources().getColor(R.color.softgreen));
                ((TextView) tr.findViewById(R.id.itemPlace)).setTextColor(getResources().getColor(R.color.softgreen));
            }
            table.addView(tr);
    }
}
