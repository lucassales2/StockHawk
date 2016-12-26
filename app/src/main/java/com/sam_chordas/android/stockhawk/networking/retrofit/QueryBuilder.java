package com.sam_chordas.android.stockhawk.networking.retrofit;

/**
 * Created by lucas on 26/12/16.
 */

public class QueryBuilder {

    public static String buildQuoteHistoryQuery(String symbol, String startDate, String endDate) {
        return "select * from yahoo.finance.historicaldata where symbol=\"" +
                symbol +
                "\" and startDate=\"" + startDate + "\" and endDate=\"" + endDate + "\"";

    }
}
