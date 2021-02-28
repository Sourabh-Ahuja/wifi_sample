package com.sourabh.openapp.repo;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.sourabh.openapp.database.AppDataBase;
import com.sourabh.openapp.database.AppDbHelper;
import com.sourabh.openapp.di.ApplicationContext;
import com.sourabh.openapp.model.Wifi;
import com.sourabh.openapp.utils.SingleLiveEvent;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WifiRepository {

    private static final String TAG = "WifiRepository";

    public final AppDbHelper appDbHelper;

    public Context context;

    private WifiManager wifiManager;

    public SingleLiveEvent<List<ScanResult>> scanLiveEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> booleanSingleEvent = new SingleLiveEvent<>();


    @Inject
    public WifiRepository(Context context, AppDbHelper appDbHelper) {
        this.appDbHelper = appDbHelper;
        this.context = context;
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    private boolean checkWifiScan() {
        if(!wifiManager.isWifiEnabled()){
            Toast.makeText(context, "Wifi is not enable. Turning on wifi",
                    Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }
        return wifiManager.startScan();
    }

    public LiveData<List<ScanResult>> getWifiList() {
        Log.e(TAG,"getWifiList " + checkWifiScan());
        scanLiveEvent.setValue(getWifiScanResult());
        return scanLiveEvent;
    }

    private List<ScanResult> getWifiScanResult(){
        return wifiManager.getScanResults();
    }

    public LiveData<Boolean> connectToWifi(String wifiName, String password){
        booleanSingleEvent.setValue(true);
        return booleanSingleEvent;
    }


}
