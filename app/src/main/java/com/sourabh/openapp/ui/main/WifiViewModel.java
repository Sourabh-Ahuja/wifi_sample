package com.sourabh.openapp.ui.main;

import android.net.wifi.ScanResult;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.sourabh.openapp.model.Wifi;
import com.sourabh.openapp.repo.WifiRepository;
import com.sourabh.openapp.ui.base.BaseViewModel;
import com.sourabh.openapp.utils.SchedulerProvider;
import com.sourabh.openapp.utils.SingleLiveEvent;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class WifiViewModel extends BaseViewModel {

    private static final String TAG = "WifiViewModel";


    public SingleLiveEvent<List<ScanResult>> wifiLiveEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> booleanSingleLiveEvent = new SingleLiveEvent<>();


    public WifiViewModel(WifiRepository wifiRepository, SchedulerProvider schedulerProvider) {
        super(wifiRepository,schedulerProvider);
    }

    public void fetchMovieList() {

//        Disposable disposable = getAppDataManager().fetchNewsList()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(moviesListResponseResponse -> {
//                    Log.e(TAG, "moviesListResponseResponse " +
//                            moviesListResponseResponse);
//                    Log.e(TAG, "moviesListResponseResponse 1" + moviesListResponseResponse.
//                            body());
//
//                    moviesListLiveEvent.setValue(moviesListResponseResponse.body().getNewsList());
//
//                    saveDataToDB(moviesListResponseResponse.body().getNewsList());
//
//                }, throwable -> {
//
//                    //showToastMessage(throwable.getMessage());
//
//                    fetchDataFromDb();
//                });
//
//        getCompositeDisposable().add(disposable);
    }

    public LiveData<List<ScanResult>> getWifiList() {
        return wifiRepository.getWifiList();
    }

    public LiveData<Boolean> observeResult() {
        return booleanSingleLiveEvent;
    }

    public void connectToWifi(String wifiName, String password) {
         booleanSingleLiveEvent.setValue(wifiRepository.connectToWifi(wifiName,password).getValue());
    }


    private void saveDataToDB(List<Wifi> movieList) {
//        Disposable disposable = getAppDataManager().insertNewsList(movieList)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(successValue -> Log.d("DataBase : ", "saving success"),
//                        throwable -> Log.d("DataBase : ", throwable.getMessage()));
//
//        getCompositeDisposable().add(disposable);
    }

    private void fetchDataFromDb() {

//        Disposable disposable = getAppDataManager().getAllNews()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(movieList -> moviesListLiveEvent.setValue(movieList), throwable -> {
//                    Log.d("DataBase : ", throwable.getMessage());
//                });
//        getCompositeDisposable().add(disposable);

    }

}
