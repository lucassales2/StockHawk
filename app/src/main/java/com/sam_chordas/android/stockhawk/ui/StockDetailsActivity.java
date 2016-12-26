package com.sam_chordas.android.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.service.Constants;
import com.sam_chordas.android.stockhawk.service.StockIntentService;

public class StockDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);
        String symbol = getIntent().getStringExtra(Constants.SYMBOL);
        final Intent mServiceIntent = new Intent(this, StockIntentService.class);
        mServiceIntent.setAction(Constants.ACTION_STOCK_HISTORY);
        mServiceIntent.putExtra(Constants.SYMBOL, symbol);
        startService(mServiceIntent);

    }
}
