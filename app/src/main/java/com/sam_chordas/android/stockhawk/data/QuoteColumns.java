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

    @DataType(TEXT)
    @NotNull
    String OPEN = "open";

    @DataType(TEXT)
    @NotNull
    String DAYS_LOW = "days_low";
    @DataType(TEXT)
    @NotNull
    String DAYS_HIGH = "days_high";

    @DataType(TEXT)
    @NotNull
    String YEAR_LOW = "year_low";

    @DataType(TEXT)
    @NotNull
    String YEAR_HIGH = "year_high";

    @DataType(INTEGER)
    @NotNull
    String ISCURRENT = "is_current";

    @DataType(INTEGER)
    @NotNull
    String LAST_UPDATE = "last_update";

    @DataType(TEXT)
    @NotNull
    String CURRENCY = "currency";

}
