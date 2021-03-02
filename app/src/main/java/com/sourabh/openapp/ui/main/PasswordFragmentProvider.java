package com.sourabh.openapp.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * use for dagger
 * **/
@Module
public abstract class PasswordFragmentProvider {
    @ContributesAndroidInjector(modules = {
            PasswordFragmentModule.class})
    abstract PasswordFragment passwordFragment();
}
