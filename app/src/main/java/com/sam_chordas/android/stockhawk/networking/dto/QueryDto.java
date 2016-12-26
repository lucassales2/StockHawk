package com.sam_chordas.android.stockhawk.networking.dto;

/**
 * Created by lucas on 19/12/16.
 */

public class QueryDto {
    private String Symbol;
    private String Date;
    private String Low;
    private String High;
    private String Open;

    public String getHigh() {
        return High;
    }

    public String getLow() {
        return Low;
    }

    public String getSymbol() {
        return Symbol;
    }

    public String getDate() {
        return Date;
    }

    public String getOpen() {
        return Open;
    }
}
