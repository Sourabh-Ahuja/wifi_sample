package com.sourabh.openapp.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sourabh.openapp.database.AppDataBase;
import com.sourabh.openapp.database.AppDbHelper;
import com.sourabh.openapp.database.AppDbHelperImpl;
import com.sourabh.openapp.di.ApplicationContext;
import com.sourabh.openapp.repo.WifiRepository;
import com.sourabh.openapp.utils.AppSchedulerProvider;
import com.sourabh.openapp.utils.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
//  providing dependency for app use

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    Gson provideGsonBuilder() {

        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    WifiRepository provideWifiRepository(@ApplicationContext Context context) {
        return new WifiRepository(context);
    }

    @Provides
    @Singleton
    AppDbHelper provideAppDbHelper(AppDataBase appDataBase) {
        return new AppDbHelperImpl(appDataBase);
    }

}
