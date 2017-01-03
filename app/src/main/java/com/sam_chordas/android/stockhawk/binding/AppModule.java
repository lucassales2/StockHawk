package com.sam_chordas.android.stockhawk.binding;

import android.content.Context;
import android.graphics.Typeface;

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

    private Context mContext;

    public AppModule(Context context) {

        mContext = context;
    }

    @Provides
    @Singleton
    SimpleDateFormat provideSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @Provides
    @Singleton
    Typeface provideDefaultFont() {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");

    }
}
