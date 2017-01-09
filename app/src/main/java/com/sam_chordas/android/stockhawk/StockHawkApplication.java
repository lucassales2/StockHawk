package com.sam_chordas.android.stockhawk;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.sam_chordas.android.stockhawk.binding.AppModule;
import com.sam_chordas.android.stockhawk.binding.ApplicationComponent;
import com.sam_chordas.android.stockhawk.binding.DaggerApplicationComponent;
import com.sam_chordas.android.stockhawk.binding.NetworkModule;

import io.fabric.sdk.android.Fabric;

/**
 * Created by lucas on 19/12/16.
 */

public class StockHawkApplication extends Application {

    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        component = DaggerApplicationComponent.builder()
                .appModule(new AppModule())
                .networkModule(new NetworkModule())
                .build();
    }
}
