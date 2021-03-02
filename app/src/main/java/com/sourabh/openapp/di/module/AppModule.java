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
// providing dependency for app use
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


}
