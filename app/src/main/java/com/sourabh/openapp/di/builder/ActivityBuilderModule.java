package com.sourabh.openapp.di.builder;


import com.sourabh.openapp.di.main.WifiActivityModule;
import com.sourabh.openapp.ui.main.PasswordFragmentProvider;
import com.sourabh.openapp.ui.main.WifiActivity;
import com.sourabh.openapp.ui.main.WifiListFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
            modules = {
                    WifiActivityModule.class,
                    WifiListFragmentProvider.class,
                    PasswordFragmentProvider.class
            }
    )
    abstract WifiActivity wifiActivity();
}
