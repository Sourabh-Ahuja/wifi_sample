package com.sourabh.openapp.di.module;


import android.app.Application;
import android.content.Context;
import androidx.room.Room;

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
public class AppModule {

    @Provides
    @ApplicationContext
    Context providesContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    AppDataBase providesAppDataBase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDataBase.class,
                "OpenappDatabase").fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    AppDbHelper provideAppDbHelper(AppDbHelperImpl dbHelper) {
        return dbHelper;
    }

    @Provides
    @Singleton
    Gson provideGsonBuilder() {

        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    WifiRepository provideSchedulerProvider(@ApplicationContext Context context,
                                            AppDbHelper appDbHelper) {
        return new WifiRepository(context, appDbHelper);
    }

}
