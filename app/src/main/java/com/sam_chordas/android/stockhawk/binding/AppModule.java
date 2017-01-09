package com.sam_chordas.android.stockhawk.binding;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lucas on 28/12/16.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    SimpleDateFormat provideSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

}
