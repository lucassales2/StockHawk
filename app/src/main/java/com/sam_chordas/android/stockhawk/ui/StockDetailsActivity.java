package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.service.Constants;

public class StockDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        if (savedInstanceState == null) {
            StockDetailsFragment fragment = StockDetailsFragment.newInstance(getIntent().getStringExtra(Constants.SYMBOL));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stock_detail_container, fragment)
                    .commit();
        }

    }
}
