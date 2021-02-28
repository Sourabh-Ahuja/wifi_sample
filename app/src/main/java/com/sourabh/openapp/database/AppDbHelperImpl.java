package com.sourabh.openapp.database;


import com.sourabh.openapp.model.Wifi;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Single;


@Singleton
public class AppDbHelperImpl implements AppDbHelper {

    public final AppDataBase appDataBase;

    @Inject
    AppDbHelperImpl(AppDataBase appDataBase) {
        this.appDataBase = appDataBase;
    }

    @Override
    public Single<List<Long>> insertWifiList(List<Wifi> wifiList) {
        List<Long> longList = new ArrayList<>();
        return Single.fromCallable(() -> {
            for(Wifi entityNews : wifiList){
                longList.add(appDataBase.getDaoWifi().insertWifi(entityNews));
            }
            return longList;
        });
    }

    @Override
    public Single<List<Wifi>> getAllWifi() {
        return appDataBase.getDaoWifi().getAllWifi();
    }
}
