package com.sam_chordas.android.stockhawk.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by lucas on 19/12/16.
 */

public interface QuoteHistoryColumns {

    @PrimaryKey
    @DataType(INTEGER)
    @NotNull
    String DATE = "date";

    @PrimaryKey
    @DataType(TEXT)
    @NotNull
    String SYMBOL = "symbol";


    @DataType(REAL)
    @NotNull
    String BIDPRICE = "bid_price";

}
