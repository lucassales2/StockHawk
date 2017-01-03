package com.sam_chordas.android.stockhawk.data;

import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKeyConstraint;

import static com.sam_chordas.android.stockhawk.data.QuoteDatabase.QUOTES_HISTORY;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by lucas on 19/12/16.
 */

@PrimaryKeyConstraint(
        columns = {QuoteHistoryColumns.DATE, QuoteHistoryColumns.SYMBOL},
        name = QUOTES_HISTORY,
        onConflict = ConflictResolutionType.IGNORE
)
public interface QuoteHistoryColumns {


    @DataType(TEXT)
    @NotNull
    String DATE = "date";

    @DataType(TEXT)
    @NotNull
    String SYMBOL = "symbol";

    @DataType(REAL)
    @NotNull
    String BIDPRICE = "bid_price";

}
