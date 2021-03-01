package com.sourabh.openapp.repo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
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
    public SingleLiveEvent<Boolean> booleanSingleLiveEvent = new SingleLiveEvent<>();

    @Inject
    public WifiRepository(Context context, AppDbHelper appDbHelper) {
        this.appDbHelper = appDbHelper;
        this.context = context;
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    private boolean checkWifiScan() {
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(context, "Wifi is not enable. Turning on wifi",
                    Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }
        return wifiManager.startScan();
    }

    public LiveData<List<ScanResult>> getWifiList() {
        Log.e(TAG, "getWifiList " + checkWifiScan());
        scanLiveEvent.setValue(getWifiScanResult());
        return scanLiveEvent;
    }

    private List<ScanResult> getWifiScanResult() {
        return wifiManager.getScanResults();
    }

    public boolean connectToWifi(String networkSSID, String networkPassword) {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }


        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = String.format("\"%s\"", networkSSID);
        conf.preSharedKey = String.format("\"%s\"", networkPassword);
        int netId = wifiManager.addNetwork(conf);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        Log.e(TAG,"connectToWifi called netId " + netId + " networkPassword " + networkPassword);

        return wifiManager.reconnect();
    }

    public boolean connectToOpenWifi(String networkSSID){
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = String.format("\"%s\"", networkSSID);
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        int netId = wifiManager.addNetwork(conf);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        return wifiManager.reconnect();
    }




}
