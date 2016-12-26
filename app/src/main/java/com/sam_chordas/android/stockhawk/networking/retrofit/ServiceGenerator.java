package com.sam_chordas.android.stockhawk.networking.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lucas on 19/12/16.
 */

public class ServiceGenerator {
    public static final String API_BASE_URL = "https://query.yahooapis.com/";

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static QuoteApiService createService() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(getInterceptor());
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(QuoteApiService.class);
    }

    private static Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("format", "json")
                        .addQueryParameter("diagnostics", "true")
                        .addQueryParameter("lang", String.format("%s-%s", Locale.getDefault().getLanguage(), Locale.getDefault().getCountry()))
                        .addEncodedQueryParameter("env", "store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
                        .addQueryParameter("callback", "")
//                        .addQueryParameter("lang", String.format("%s-%s", Locale.getDefault().getLanguage(), Locale.getDefault().getCountry()))
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }
}
