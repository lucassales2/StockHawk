package com.sam_chordas.android.stockhawk.rest;

import android.content.ContentProviderOperation;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.networking.dto.QuoteDto;
import com.sam_chordas.android.stockhawk.networking.response.StockResponse;

import java.util.ArrayList;

/**
 * Created by sam_chordas on 10/8/15.
 */
public class Utils {

    public static boolean showPercent = true;
    private static String LOG_TAG = Utils.class.getSimpleName();

    public static ArrayList<ContentProviderOperation> quoteReponseToContentVals(StockResponse response) {
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        for (QuoteDto quoteDto : response.getQuery().getQuote().getQuotes()) {
            batchOperations.add(buildBatchOperation(quoteDto));
        }


        return batchOperations;
    }

    public static String truncateBidPrice(String bidPrice) {
        return String.format("%.2f", Float.parseFloat(bidPrice));
    }

    public static String truncateChange(String change, boolean isPercentChange) {
        String weight = change.substring(0, 1);
        String ampersand = "";
        if (isPercentChange) {
            ampersand = change.substring(change.length() - 1, change.length());
            change = change.substring(0, change.length() - 1);
        }
        change = change.substring(1, change.length());
        double round = (double) Math.round(Double.parseDouble(change) * 100) / 100;
        change = String.format("%.2f", round);
        StringBuffer changeBuffer = new StringBuffer(change);
        changeBuffer.insert(0, weight);
        changeBuffer.append(ampersand);
        change = changeBuffer.toString();
        return change;
    }

    public static ContentProviderOperation buildBatchOperation(QuoteDto quoteDto) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                QuoteProvider.Quotes.CONTENT_URI);
        String change = quoteDto.getChange();
        builder.withValue(QuoteColumns.SYMBOL, quoteDto.getSymbol());
        builder.withValue(QuoteColumns.BIDPRICE, truncateBidPrice(quoteDto.getBid()));
        builder.withValue(QuoteColumns.PERCENT_CHANGE, truncateChange(
                quoteDto.getChangeinPercent(), true));
        builder.withValue(QuoteColumns.CHANGE, truncateChange(change, false));
        builder.withValue(QuoteColumns.ISCURRENT, 1);
        builder.withValue(QuoteColumns.NAME, quoteDto.getName());
        builder.withValue(QuoteColumns.ISUP, change.charAt(0) == '-' ? 0 : 1);
        builder.withValue(QuoteColumns.OPEN, quoteDto.getOpen());
        builder.withValue(QuoteColumns.YEAR_HIGH, quoteDto.getYearHigh());
        builder.withValue(QuoteColumns.YEAR_LOW, quoteDto.getYearLow());
        builder.withValue(QuoteColumns.DAYS_HIGH, quoteDto.getDaysHigh());
        builder.withValue(QuoteColumns.DAYS_LOW, quoteDto.getDaysLow());
        builder.withValue(QuoteColumns.CURRENCY, quoteDto.getCurrency());
        builder.withValue(QuoteColumns.LAST_UPDATE, System.currentTimeMillis());

        return builder.build();
    }

}
