package com.sam_chordas.android.stockhawk.service;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.StockHawkApplication;
import com.sam_chordas.android.stockhawk.data.QuoteHistoryColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.networking.dto.QuoteHistoryDto;
import com.sam_chordas.android.stockhawk.networking.response.StockHistoryResponse;
import com.sam_chordas.android.stockhawk.networking.retrofit.QuoteApiService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by lucas on 19/12/16.
 */

public class StockHistoryTaskService extends GcmTaskService {

    @Inject
    QuoteApiService quoteApiService;
    private Context mContext;
    private String LOG_TAG = StockHistoryTaskService.class.getSimpleName();
    private StringBuilder mStoredSymbols = new StringBuilder();
    private boolean isUpdate;

    public StockHistoryTaskService() {
        this(StockHawkApplication.getInstance());
    }

    public StockHistoryTaskService(Context context) {
        mContext = context.getApplicationContext();
        StockHawkApplication.getInstance().getComponent().inject(this);
    }


    @Override
    public int onRunTask(TaskParams params) {
        try {
            Call<StockHistoryResponse> stockHistoricalData = quoteApiService.getStockHistoricalData(buildQuoteHistoryQuery(params.getExtras().getString(Constants.SYMBOL), "2016-10-26", "2016-12-26"));

            retrofit2.Response<StockHistoryResponse> response = stockHistoricalData.execute();
            StockHistoryResponse body = response.body();
            mContext.getContentResolver().applyBatch(QuoteProvider.AUTHORITY,
                    quoteReponseToContentVals(body));

            return GcmNetworkManager.RESULT_SUCCESS;

        } catch (IOException | OperationApplicationException | RemoteException | ParseException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            return GcmNetworkManager.RESULT_FAILURE;
        }
    }

    private ArrayList<ContentProviderOperation> quoteReponseToContentVals(StockHistoryResponse body) throws ParseException {
        ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        for (QuoteHistoryDto quoteHistoryDto : body.getQuery().getQuote().getStockQuotes()) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                    QuoteProvider.QuotesHistory.CONTENT_URI);
            builder.withValue(QuoteHistoryColumns.SYMBOL, quoteHistoryDto.getSymbol());
            builder.withValue(QuoteHistoryColumns.DATE, dateFormat.parse(quoteHistoryDto.getDate()).getTime());
            builder.withValue(QuoteHistoryColumns.BIDPRICE, quoteHistoryDto.getClose());
            contentProviderOperations.add(builder.build());
        }

        return contentProviderOperations;
    }

    private String buildQuoteHistoryQuery(String symbol, String startDate, String endDate) {

        return String.format("select * from yahoo.finance.historicaldata where symbol=\"%s\" and startDate=\"%s\" and endDate=\"%s\"", symbol, startDate, endDate);

    }
}
