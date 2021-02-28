package com.sourabh.openapp.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sourabh.openapp.AppConstants;
import com.sourabh.openapp.model.Wifi;


@Database(entities = {Wifi.class,}, version = AppConstants.APP_DB_VERSION)
public abstract class AppDataBase extends RoomDatabase {

    public abstract DaoWifi getDaoWifi();

}
