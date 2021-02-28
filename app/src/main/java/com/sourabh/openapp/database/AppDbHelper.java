package com.sourabh.openapp.database;


import com.sourabh.openapp.model.Wifi;
import java.util.List;
import io.reactivex.Single;


public interface AppDbHelper {

    Single<List<Long>> insertWifiList(List<Wifi> news);

    Single<List<Wifi>> getAllWifi();

}
