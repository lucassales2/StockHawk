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
