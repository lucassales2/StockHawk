package com.sam_chordas.android.stockhawk.binding;

import com.sam_chordas.android.stockhawk.networking.retrofit.QuoteApiService;
import com.sam_chordas.android.stockhawk.networking.retrofit.ServiceGenerator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lucas on 09/10/16.
 */
@Module
public class NetworkModule {


    @Provides
    @Singleton
    QuoteApiService provideQuoteApiService() {
        return ServiceGenerator.createService();
    }


}
