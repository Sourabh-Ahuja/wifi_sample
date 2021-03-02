package com.sourabh.openapp;

import android.app.Activity;
import android.app.Application;

import com.sourabh.openapp.di.AppComponent;
import com.sourabh.openapp.di.DaggerAppComponent;

import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * OpenAppApplication class for the dagger implemtation
 * */
public class OpenAppApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}

