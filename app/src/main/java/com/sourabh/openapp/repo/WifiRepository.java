package com.sourabh.openapp.repo;

import android.content.Context;

import com.sourabh.openapp.database.AppDataBase;
import com.sourabh.openapp.database.AppDbHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WifiRepository {

    public final AppDbHelper appDbHelper;

    public Context context;

    @Inject
    public WifiRepository(Context context, AppDbHelper appDbHelper) {
        this.appDbHelper = appDbHelper;
        this.context = context;
    }
}
