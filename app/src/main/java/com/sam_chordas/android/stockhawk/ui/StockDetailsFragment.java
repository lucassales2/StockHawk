package com.sam_chordas.android.stockhawk.ui;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteHistoryColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.service.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lucas on 26/12/16.
 */

public class StockDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 671;

    private LineChartView lineChartView;

    public static StockDetailsFragment newInstance(String symbol) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SYMBOL, symbol);
        StockDetailsFragment fragment = new StockDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (savedInstanceState == null) {
//            final Intent mServiceIntent = new Intent(getActivity(), StockIntentService.class);
//            mServiceIntent.setAction(Constants.ACTION_STOCK_HISTORY);
//            mServiceIntent.putExtra(Constants.SYMBOL, getArguments().getString(Constants.SYMBOL));
//            getActivity().startService(mServiceIntent);
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        lineChartView = (LineChartView) rootView.findViewById(R.id.linechart);

        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), QuoteProvider.QuotesHistory.CONTENT_URI,
                new String[]{QuoteHistoryColumns.DATE, QuoteHistoryColumns.BIDPRICE},
                QuoteHistoryColumns.SYMBOL + " = ?",
                new String[]{getArguments().getString(Constants.SYMBOL)},
                QuoteHistoryColumns.DATE + " desc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (loader.getId() == LOADER_ID && cursor.moveToFirst()) {
            drawChart(cursor);
        }

    }

    private void drawChart(Cursor cursor) {

        LineSet lineSet = new LineSet();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (cursor.moveToFirst()) {
            do {
                float value = cursor.getFloat(1);
                Point point = new Point(dateFormat.format(new Date(cursor.getLong(0))), value);
                lineSet.addPoint(point);
            } while (cursor.moveToNext());
        }
        lineSet.setColor(Color.WHITE);

        lineChartView.addData(lineSet);
        lineChartView
                .setXLabels(AxisRenderer.LabelPosition.NONE)
                .setAxisBorderValues((int) Math.floor(lineSet.getMin().getValue() - 1), Math.round(lineSet.getMax().getValue() + 1))
                .setXAxis(false)
                .setYAxis(false);
        lineChartView.show();


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
