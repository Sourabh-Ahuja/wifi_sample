package com.sourabh.openapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sourabh.openapp.model.Wifi;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface DaoWifi {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertWifiList(List<Wifi> wifiList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertWifi(Wifi news);

    @Query("SELECT * FROM Wifi")
    Single<List<Wifi>> getAllWifi();
}

