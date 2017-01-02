package com.sam_chordas.android.stockhawk.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by sam_chordas on 10/5/15.
 */
public interface QuoteColumns {
    @DataType(INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(TEXT)
    @NotNull
    String SYMBOL = "symbol";

    @DataType(TEXT)
    @NotNull
    String PERCENT_CHANGE = "percent_change";

    @DataType(TEXT)
    @NotNull
    String CHANGE = "change";

    @DataType(TEXT)
    @NotNull
    String BIDPRICE = "bid_price";

    @DataType(TEXT)
    @NotNull
    String NAME = "name";

    @DataType(TEXT)
    String CREATED = "created";

    @DataType(INTEGER)
    @NotNull
    String ISUP = "is_up";

    @DataType(INTEGER)
    @NotNull
    String ISCURRENT = "is_current";
}
