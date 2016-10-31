package com.sam_chordas.android.stockhawk.networking.dto;

/**
 * Created by lucas on 31/10/16.
 */

public class QuoteHistoryDto {
    private String Symbol;
    private String Date;
    private double Open;
    private double High;
    private double Low;
    private double Close;
    private long Volume;
    private double Adj_Close;

    public double getAdj_Close() {
        return Adj_Close;
    }

    public double getClose() {
        return Close;
    }

    public String getDate() {
        return Date;
    }

    public double getHigh() {
        return High;
    }

    public double getLow() {
        return Low;
    }

    public double getOpen() {
        return Open;
    }

    public String getSymbol() {
        return Symbol;
    }

    public long getVolume() {
        return Volume;
    }
}
