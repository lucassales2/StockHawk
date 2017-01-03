package com.sam_chordas.android.stockhawk.networking.dto;

/**
 * Created by lucas on 31/10/16.
 */

public class QuoteDto {
    private String Symbol;
    private String Change;
    private String Bid;
    private String ChangeinPercent;
    private String Name;
    private String DaysLow;
    private String DaysHigh;
    private String YearLow;
    private String YearHigh;
    private String Open;
    private String Currency;

    public String getCurrency() {
        return Currency;
    }

    public String getOpen() {
        return Open;
    }

    public String getYearLow() {
        return YearLow;
    }

    public String getDaysHigh() {
        return DaysHigh;
    }

    public String getDaysLow() {
        return DaysLow;
    }

    public String getYearHigh() {
        return YearHigh;
    }

    public String getBid() {
        return Bid;
    }

    public String getChange() {
        return Change;
    }

    public String getChangeinPercent() {
        return ChangeinPercent;
    }

    public String getName() {
        return Name;
    }

    public String getSymbol() {
        return Symbol;
    }


}
