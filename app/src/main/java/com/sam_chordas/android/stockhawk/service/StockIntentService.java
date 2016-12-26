package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.TaskParams;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {


    public StockIntentService() {
        super(StockIntentService.class.getName());
    }

    public StockIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
            if (intent.getAction().equals(Constants.ACTION_STOCKS)) {
                StockTaskService stockTaskService = new StockTaskService(this);
                Bundle args = new Bundle();
                if (intent.getStringExtra(Constants.TAG).equals(Constants.ADD)) {
                    args.putString(Constants.SYMBOL, intent.getStringExtra(Constants.SYMBOL));
                }
                // We can call OnRunTask from the intent service to force it to run immediately instead of
                // scheduling a task.
                stockTaskService.onRunTask(new TaskParams(intent.getStringExtra(Constants.TAG), args));
            } else if (intent.getAction().equals(Constants.ACTION_STOCK_HISTORY)) {
                StockHistoryTaskService stockHistoryTaskService = new StockHistoryTaskService(this);
                Bundle args = new Bundle();
                args.putString(Constants.SYMBOL, intent.getStringExtra(Constants.SYMBOL));
                // We can call OnRunTask from the intent service to force it to run immediately instead of
                // scheduling a task.
                stockHistoryTaskService.onRunTask(new TaskParams(intent.getStringExtra(Constants.HISTORY), args));
            }
        }
    }
}
