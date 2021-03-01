package com.sourabh.openapp.ui.main;

import android.net.wifi.ScanResult;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.sourabh.openapp.database.AppDbHelper;
import com.sourabh.openapp.model.Wifi;
import com.sourabh.openapp.repo.WifiRepository;
import com.sourabh.openapp.ui.base.BaseViewModel;
import com.sourabh.openapp.utils.SchedulerProvider;
import com.sourabh.openapp.utils.SingleLiveEvent;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class WifiViewModel extends BaseViewModel {

    private static final String TAG = "WifiViewModel";


    public SingleLiveEvent<Boolean> dBBooleanEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> booleanSingleLiveEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Wifi> wifiSingleLiveEvent = new SingleLiveEvent<>();

    public WifiViewModel(WifiRepository wifiRepository,
                         SchedulerProvider schedulerProvider, AppDbHelper appDbHelper) {
        super(wifiRepository,schedulerProvider, appDbHelper);
    }

    public LiveData<List<ScanResult>> getWifiList() {
        return wifiRepository.getWifiList();
    }

    public LiveData<Boolean> observeResult() {
        return booleanSingleLiveEvent;
    }

    public void connectToWifi(String wifiName, String password) {
         Wifi wifi = new Wifi(wifiName);
         wifi.setWifiPassword(password);
         if(wifiRepository.connectToWifi(wifiName,password)){
             booleanSingleLiveEvent.setValue(true);
             saveConnectedWifiToDB(wifi);
         } else {
             booleanSingleLiveEvent.setValue(false);
         }
    }

    private void saveConnectedWifiToDB(Wifi wifi) {
        Disposable disposable = getAppDbHelper().insertWifi(wifi)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(successValue -> Log.d("DataBase : ", "saving success"),
                        throwable -> Log.d("DataBase : ", throwable.getMessage()));

        getCompositeDisposable().add(disposable);
    }

    public LiveData<Wifi> getWifiLiveData() {
        return wifiSingleLiveEvent;
    }

    private void setWifiSingleLiveEvent(Wifi wifi) {
        wifiSingleLiveEvent.setValue(wifi);
    }

    public LiveData<Boolean> observeFromDb() {
        return dBBooleanEvent;
    }

    public void fetchDataFromDb(Wifi wifi) {
        setWifiSingleLiveEvent(wifi);
        Disposable disposable = getAppDbHelper().getAllWifi()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(wifiList -> {
                    Log.e(TAG,"wifiList " + wifiList.toString());
                    boolean isNetworkSavedInDb = false;
                           for(Wifi dbwifi : wifiList){
                               if(dbwifi.getWifiName().equalsIgnoreCase(wifi.getWifiName())){
                                   isNetworkSavedInDb = true;
                                   booleanSingleLiveEvent.setValue(wifiRepository.
                                           connectToWifi(dbwifi.getWifiName(),dbwifi.getWifiPassword()));
                                   break;
                               }
                           }
                          dBBooleanEvent.setValue(isNetworkSavedInDb);
                        }
                        ,throwable -> {
                    Log.d("DataBase : ", throwable.getMessage());
                });
        getCompositeDisposable().add(disposable);

    }

}
