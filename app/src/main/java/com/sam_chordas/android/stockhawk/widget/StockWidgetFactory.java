package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

/**
 * Created by lucas on 13/01/17.
 */

public class StockWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context mContext;
    private final int mWidgetId;
    private Cursor mCursor;

    public StockWidgetFactory(Context context, int widgetId) {
        this.mContext = context;
        this.mWidgetId = widgetId;
    }

    @Override
    public void onCreate() {
        mCursor = mContext.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                        QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                null,
                null,
                QuoteColumns.SYMBOL + " desc");
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item_quote);
        if (mCursor.moveToPosition(i)) {
            rv.setTextViewText(R.id.stock_symbol,
                    mCursor.getString(mCursor.getColumnIndex(QuoteColumns.SYMBOL)));
            rv.setTextViewText(R.id.bid_price,
                    mCursor.getString(mCursor.getColumnIndex(QuoteColumns.BIDPRICE)));
            rv.setTextViewText(R.id.change,
                    mCursor.getString(mCursor.getColumnIndex(QuoteColumns.PERCENT_CHANGE)));
            rv.setTextColor(R.id.change,
                    mCursor.getInt(mCursor.getColumnIndex(QuoteColumns.ISUP)) == 1 ? ContextCompat.getColor(mContext, R.color.material_green_700) : ContextCompat.getColor(mContext, R.color.material_red_700));

        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        if (mCursor.move(i)) {
            return mCursor.getLong(mCursor.getColumnIndex(QuoteColumns._ID));
        }
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
