package com.sourabh.openapp.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WifiListFragmentProvider {

    @ContributesAndroidInjector(modules = {
            WifiListFragmentModule.class,
    })
    abstract WifiListFragment wifiListFragment();

}
