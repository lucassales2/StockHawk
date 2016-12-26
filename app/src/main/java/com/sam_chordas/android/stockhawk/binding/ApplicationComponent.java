package com.sam_chordas.android.stockhawk.binding;

import com.sam_chordas.android.stockhawk.service.StockHistoryTaskService;
import com.sam_chordas.android.stockhawk.service.StockTaskService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lucas on 19/12/16.
 */

@Singleton
@Component(modules = {NetworkModule.class})
public interface ApplicationComponent {
    void inject(StockTaskService stockTaskService);

    void inject(StockHistoryTaskService stockHistoryTaskService);
}
