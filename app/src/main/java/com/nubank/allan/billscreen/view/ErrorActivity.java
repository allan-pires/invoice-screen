package com.nubank.allan.billscreen.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nubank.allan.billscreen.R;

/**
 * Created by doisl_000 on 1/17/2016.
 *
 */
public class ErrorActivity extends Activity {

    private TextView errorCode;
    private TextView errorTxt;
    private Button retryButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error);

        errorCode = (TextView) this.findViewById(R.id.errorCode);
        errorTxt = (TextView) this.findViewById(R.id.errorText);
        retryButton = (Button) findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restartMainActivity();
            }
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                errorCode.setText(extras.getString("error_code"));
                errorTxt.setText(extras.getString("error_message"));
            }
        }
    }

    public void restartMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
