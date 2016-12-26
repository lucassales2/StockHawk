package com.sam_chordas.android.stockhawk.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.StockHawkApplication;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.networking.response.StockResponse;
import com.sam_chordas.android.stockhawk.networking.retrofit.QuoteApiService;
import com.sam_chordas.android.stockhawk.rest.Utils;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by sam_chordas on 9/30/15.
 * The GCMTask service is primarily for periodic tasks. However, OnRunTask can be called directly
 * and is used for the initialization and adding task as well.
 */
public class StockTaskService extends GcmTaskService {
    @Inject
    QuoteApiService apiService;
    private Context mContext;
    private String LOG_TAG = StockTaskService.class.getSimpleName();
    private OkHttpClient client = new OkHttpClient();
    private StringBuilder mStoredSymbols = new StringBuilder();
    private boolean isUpdate;


    public StockTaskService() {
    }

    public StockTaskService(Context context) {
        StockHawkApplication.getInstance().getComponent().inject(this);
        mContext = context.getApplicationContext();
    }


    @Override
    public int onRunTask(TaskParams params) {
        if (mContext == null) {
            return GcmNetworkManager.RESULT_FAILURE;
        }
        StockResponse getResponse;
        int result = GcmNetworkManager.RESULT_FAILURE;

        try {
            Call<StockResponse> call = apiService.getStock(buildQuoteQuery(params));
            getResponse = call.execute().body();
            result = GcmNetworkManager.RESULT_SUCCESS;
            try {
                ContentValues contentValues = new ContentValues();
                // update ISCURRENT to 0 (false) so new data is current
                if (isUpdate) {
                    contentValues.put(QuoteColumns.ISCURRENT, 0);
                    mContext.getContentResolver().update(QuoteProvider.Quotes.CONTENT_URI, contentValues,
                            null, null);
                }
                mContext.getContentResolver().applyBatch(QuoteProvider.AUTHORITY,
                        Utils.quoteJsonToContentVals(getResponse));
            } catch (RemoteException | OperationApplicationException e) {
                Log.e(LOG_TAG, "Error applying batch insert", e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String buildQuoteQuery(TaskParams params) {
        Cursor initQueryCursor;
        StringBuilder urlStringBuilder = new StringBuilder();
        urlStringBuilder.append("select * from yahoo.finance.quotes where symbol "
                + "in (");

        if (params.getTag().equals(Constants.INIT) || params.getTag().equals(Constants.PERIODIC)) {
            isUpdate = true;
            initQueryCursor = mContext.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                    new String[]{"Distinct " + QuoteColumns.SYMBOL}, null,
                    null, null);
            if (initQueryCursor.getCount() == 0 || initQueryCursor == null) {
                // Init task. Populates DB with quotes for the symbols seen below
                urlStringBuilder.append("\"YHOO\",\"AAPL\",\"GOOG\",\"MSFT\")");
            } else if (initQueryCursor != null) {
                DatabaseUtils.dumpCursor(initQueryCursor);
                if (initQueryCursor.moveToFirst()) {
                    do {
                        mStoredSymbols.append("\"" +
                                initQueryCursor.getString(initQueryCursor.getColumnIndex("symbol")) + "\",");
                    } while (initQueryCursor.moveToNext());

                }
                mStoredSymbols.replace(mStoredSymbols.length() - 1, mStoredSymbols.length(), ")");
                urlStringBuilder.append(mStoredSymbols.toString());
            }
        } else if (params.getTag().equals(Constants.ADD)) {
            isUpdate = false;
            // get symbol from params.getExtra and build query
            String stockInput = params.getExtras().getString(Constants.SYMBOL);
            urlStringBuilder.append("\"" + stockInput + "\")");

        }
        return urlStringBuilder.toString();
    }

}
