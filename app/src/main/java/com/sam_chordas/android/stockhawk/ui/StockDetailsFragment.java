package com.sam_chordas.android.stockhawk.ui;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.StockHawkApplication;
import com.sam_chordas.android.stockhawk.data.QuoteHistoryColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.service.Constants;
import com.sam_chordas.android.stockhawk.service.StockIntentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by lucas on 26/12/16.
 */

public class StockDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 671;

    @Inject
    SimpleDateFormat simpleDateFormat;
    private LineChartView lineChartView;
    private TabLayout tabLayout;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StockHawkApplication.getInstance().getComponent().inject(this);
        if (savedInstanceState == null) {
            final Intent mServiceIntent = new Intent(getActivity(), StockIntentService.class);
            mServiceIntent.setAction(Constants.ACTION_STOCK_HISTORY);
            mServiceIntent.putExtra(Constants.SYMBOL, getArguments().getString(Constants.SYMBOL));
            getActivity().startService(mServiceIntent);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        lineChartView = (LineChartView) rootView.findViewById(R.id.linechart);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        setupTablayout();
        return rootView;
    }

    private void setupTablayout() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {

            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });

        tabLayout.addTab(tabLayout.newTab().setText(R.string.five_days));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.one_month));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.three_months));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.one_year));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.five_years));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.max_time));


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

        if (cursor.moveToLast()) {
            do {
                String label = "";
                int position = cursor.getPosition();
                int count = cursor.getCount();
                if (position == 0 || position == count - 1 || position == count / 2 || position == count / 4 || position == count * 3 / 4) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    int currentYear = calendar.get(Calendar.YEAR);
                    String dateString = cursor.getString(0);
                    try {
                        calendar.setTime(simpleDateFormat.parse(dateString));
                        if (calendar.get(Calendar.YEAR) == currentYear) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM");
                            label = dateFormat.format(calendar.getTime());
                        } else {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            label = dateFormat.format(calendar.getTime());
                        }
                    } catch (ParseException e) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        label = dateFormat.format(calendar.getTime());
                    }


                }
                float value = cursor.getFloat(1);
                Point point = new Point(label, value);
                lineSet.addPoint(point);
            } while (cursor.moveToPrevious());
        }
        lineSet.setColor(Color.WHITE);

        lineChartView.reset();
        lineChartView.addData(lineSet);
        lineChartView
//                .setXLabels(AxisRenderer.LabelPosition.NONE)
                .setLabelsColor(Color.WHITE)
                .setAxisLabelsSpacing(getResources().getDimension(R.dimen.axis_thickness))
                .setAxisBorderValues((int) Math.floor(lineSet.getMin().getValue() - 1), Math.round(lineSet.getMax().getValue() + 1))
                .setXAxis(false)
                .setYAxis(false);
        lineChartView.show();


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
