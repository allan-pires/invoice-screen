package com.nubank.allan.billscreen.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nubank.allan.billscreen.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MonthFragment extends Fragment {

    public MonthFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_month, container, false);
    }
}
