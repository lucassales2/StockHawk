package com.sam_chordas.android.stockhawk.ui.viewmodel;

import android.database.Cursor;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lucas on 03/01/17.
 */

public class QuoteViewModel {
    private String symbol;
    private String change;
    private String bid;
    private String changeinPercent;
    private String name;
    private String daysLow;
    private String daysHigh;
    private String yearLow;
    private String yearHigh;
    private String open;
    private long lastUpdate;
    private String currency;

    public QuoteViewModel(String bid, String change, String changeinPercent, String daysHigh, String daysLow, String name, String open, String symbol, String yearHigh, String yearLow, String currency, long lastUpdate) {
        this.bid = bid;
        this.change = change;
        this.changeinPercent = changeinPercent;
        this.daysHigh = daysHigh;
        this.daysLow = daysLow;
        this.name = name;
        this.open = open;
        this.symbol = symbol;
        this.yearHigh = yearHigh;
        this.yearLow = yearLow;
        this.currency = currency;
        this.lastUpdate = lastUpdate;
    }

    public QuoteViewModel(Cursor cursor) {
        this(
                cursor.getString(cursor.getColumnIndex(QuoteColumns.BIDPRICE)),
                cursor.getString(cursor.getColumnIndex(QuoteColumns.CHANGE)),
                cursor.getString(cursor.getColumnIndex(QuoteColumns.PERCENT_CHANGE)),
                cursor.getString(cursor.getColumnIndex(QuoteColumns.DAYS_HIGH)),
                cursor.getString(cursor.getColumnIndex(QuoteColumns.DAYS_LOW)),
                cursor.getString(cursor.getColumnIndex(QuoteColumns.NAME)),
                cursor.getString(cursor.getColumnIndex(QuoteColumns.OPEN)),
                cursor.getString(cursor.getColumnIndex(QuoteColumns.SYMBOL)),
                cursor.getString(cursor.getColumnIndex(QuoteColumns.YEAR_HIGH)),
                cursor.getString(cursor.getColumnIndex(QuoteColumns.YEAR_LOW)),
                cursor.getString(cursor.getColumnIndex(QuoteColumns.CURRENCY)),
                cursor.getLong(cursor.getColumnIndex(QuoteColumns.LAST_UPDATE)));
    }

    public String getCurrency() {
        return currency;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public String getOpen() {
        return open;
    }

    public String getBid() {
        return bid;
    }

    public String getChange() {
        return change;
    }

    public String getChangeinPercent() {
        return changeinPercent;
    }

    public String getDaysHigh() {
        return daysHigh;
    }

    public String getDaysLow() {
        return daysLow;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getYearHigh() {
        return yearHigh;
    }

    public String getYearLow() {
        return yearLow;
    }

    public String getSymbolAndLastUpdate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a, z", Locale.getDefault());
        return getSymbol() + " " + simpleDateFormat.format(new Date(getLastUpdate()));
    }

    public boolean isUp() {
        return change.charAt(0) == '+';
    }
}
