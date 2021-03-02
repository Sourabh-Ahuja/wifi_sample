package com.sourabh.openapp.database;


import com.sourabh.openapp.model.Wifi;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Single;

/*
* helper class for saving connected wifi network in room data
* */
@Singleton
public class AppDbHelperImpl implements AppDbHelper {

    public final AppDataBase appDataBase;

    @Inject
    public AppDbHelperImpl(AppDataBase appDataBase) {
        this.appDataBase = appDataBase;
    }

    @Override
    public Single<Long> insertWifi(Wifi wifi) {
        List<Long> longList = new ArrayList<>();
        return Single.fromCallable(() -> {
          return appDataBase.getDaoWifi().insertWifi(wifi);
        });
    }

    @Override
    public Single<List<Wifi>> getAllWifi() {
        return appDataBase.getDaoWifi().getAllWifi();
    }
}
