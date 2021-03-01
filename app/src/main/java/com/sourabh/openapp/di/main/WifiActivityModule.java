package com.sourabh.openapp.di.main;

import androidx.lifecycle.ViewModelProvider;

import com.sourabh.openapp.database.AppDbHelper;
import com.sourabh.openapp.di.module.ViewModelProviderFactory;
import com.sourabh.openapp.repo.WifiRepository;
import com.sourabh.openapp.ui.main.WifiViewModel;
import com.sourabh.openapp.utils.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class WifiActivityModule {

    @Provides
    WifiViewModel provideMainActivityViewModel(WifiRepository wifiRepository,
                                               SchedulerProvider schedulerProvider, AppDbHelper appDbHelper) {
        return new WifiViewModel(wifiRepository, schedulerProvider, appDbHelper);
    }

    @Provides
    ViewModelProvider.Factory provideViewModelFactory(WifiViewModel mainActivityViewModel) {
        return new ViewModelProviderFactory<>(mainActivityViewModel);
    }
}
