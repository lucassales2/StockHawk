package com.sam_chordas.android.stockhawk.service;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
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
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import retrofit2.Call;

import static com.google.android.gms.gcm.GcmNetworkManager.RESULT_SUCCESS;

/**
 * Created by lucas on 19/12/16.
 */

public class StockHistoryTaskService extends GcmTaskService {

    @Inject
    QuoteApiService quoteApiService;
    @Inject
    SimpleDateFormat simpleDateFormat;
    private Context mContext;
    private String LOG_TAG = StockHistoryTaskService.class.getSimpleName();

    public StockHistoryTaskService() {

    }

    public StockHistoryTaskService(Context context) {
        mContext = context.getApplicationContext();
        StockHawkApplication.getComponent().inject(this);
    }


    @Override
    public int onRunTask(TaskParams params) {
        if (mContext != null) {
            try {
                String symbol = params.getExtras().getString(Constants.SYMBOL);
                String startDateString = params.getExtras().getString(Constants.START_DATE);
                String endDateString = params.getExtras().getString(Constants.END_DATE);
                Date currentDate = new Date();
                String currentDateString = simpleDateFormat.format(currentDate);
                if (startDateString.isEmpty() || endDateString.isEmpty()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
                    if (startDateString.isEmpty())
                        startDateString = simpleDateFormat.format(calendar.getTime());
                    if (endDateString.isEmpty())
                        endDateString = currentDateString;
                }


                Cursor cursorLatestDate = mContext.getContentResolver().query(
                        QuoteProvider.QuotesHistory.CONTENT_URI,
                        new String[]{QuoteHistoryColumns.DATE},
                        QuoteHistoryColumns.SYMBOL + "=?",
                        new String[]{symbol},
                        QuoteHistoryColumns.DATE + " desc limit 1");

                Cursor cursorEarlierstDate = mContext.getContentResolver().query(
                        QuoteProvider.QuotesHistory.CONTENT_URI,
                        new String[]{QuoteHistoryColumns.DATE},
                        QuoteHistoryColumns.SYMBOL + "=?",
                        new String[]{symbol},
                        QuoteHistoryColumns.DATE + " asc limit 1");


                if (cursorEarlierstDate.moveToFirst() && cursorLatestDate.moveToFirst()) {
                    Date earliestDateInDb = simpleDateFormat.parse(cursorEarlierstDate.getString(0));
                    Date startDate = simpleDateFormat.parse(startDateString);
                    Date endDate = simpleDateFormat.parse(endDateString);
                    Date latestDateInDb = simpleDateFormat.parse(cursorLatestDate.getString(0));
                    if (startDate.after(earliestDateInDb)) {
                        startDate = latestDateInDb;
                    }
                    if (startDate.after(endDate) || startDate.equals(endDate)) {
                        return RESULT_SUCCESS;
                    }
                }

                Call<StockHistoryResponse> stockHistoricalData = quoteApiService.getStockHistoricalData(buildQuoteHistoryQuery(
                        symbol,
                        startDateString,
                        endDateString));

                retrofit2.Response<StockHistoryResponse> response = stockHistoricalData.execute();
                if (response.isSuccessful()) {
                    StockHistoryResponse body = response.body();
                    mContext.getContentResolver().applyBatch(QuoteProvider.AUTHORITY,
                            quoteReponseToContentVals(body));
                    return RESULT_SUCCESS;
                } else {
                    Log.e(LOG_TAG, response.errorBody().string());
                }
            } catch (IOException | OperationApplicationException | RemoteException | ParseException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }
        }
        return GcmNetworkManager.RESULT_FAILURE;
    }

    private ArrayList<ContentProviderOperation> quoteReponseToContentVals(StockHistoryResponse body) throws ParseException {
        ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<>();
        if (body.getQuery().getCount() > 0) {
            for (QuoteHistoryDto quoteHistoryDto : body.getQuery().getQuote().getStockQuotes()) {
                ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                        QuoteProvider.QuotesHistory.CONTENT_URI);
                builder.withValue(QuoteHistoryColumns.SYMBOL, quoteHistoryDto.getSymbol());
                builder.withValue(QuoteHistoryColumns.DATE, quoteHistoryDto.getDate());
                builder.withValue(QuoteHistoryColumns.BIDPRICE, quoteHistoryDto.getClose());
                contentProviderOperations.add(builder.build());
            }
        }
        return contentProviderOperations;
    }

    private String buildQuoteHistoryQuery(String symbol, String startDate, String endDate) {

        return String.format("select * from yahoo.finance.historicaldata where symbol=\"%s\" and startDate=\"%s\" and endDate=\"%s\"", symbol, startDate, endDate);

    }
}
