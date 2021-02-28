package com.sourabh.openapp.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class PasswordFragmentProvider {
    @ContributesAndroidInjector(modules = {
            PasswordFragmentModule.class})
    abstract PasswordFragment passwordFragment();
}
