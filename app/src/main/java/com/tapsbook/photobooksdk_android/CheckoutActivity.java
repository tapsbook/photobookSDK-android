package com.tapsbook.photobooksdk_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CheckoutActivity extends AppCompatActivity {
    public static final String EXTRA_NUMBER = "order_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        String number = getIntent().getStringExtra(EXTRA_NUMBER);

        TextView tvNumber = (TextView) findViewById(R.id.tv_number);
        tvNumber.setText(number);
    }
}
