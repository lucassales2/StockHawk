package com.sam_chordas.android.stockhawk.networking.retrofit;

import com.sam_chordas.android.stockhawk.networking.response.StockHistoryResponse;
import com.sam_chordas.android.stockhawk.networking.response.StockResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lucas on 19/12/16.
 */

public interface QuoteApiService {


    @GET("/v1/public/yql")
    Call<StockHistoryResponse> getStocks(@Query("q") String query);

    @GET("/v1/public/yql")
    Call<StockResponse> getStock(@Query("q") String query);

    @GET("/v1/public/yql")
    Call<StockHistoryResponse> getStockHistoricalData(@Query("q") String query);
}
