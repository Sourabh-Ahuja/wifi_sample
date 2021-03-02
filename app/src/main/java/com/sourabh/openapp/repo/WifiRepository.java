package com.sourabh.openapp.repo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;

import com.sourabh.openapp.database.AppDataBase;
import com.sourabh.openapp.database.AppDbHelper;
import com.sourabh.openapp.di.ApplicationContext;
import com.sourabh.openapp.model.Wifi;
import com.sourabh.openapp.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
/**
 * WifiRepository for the doing the all wifi operation
 * */
@Singleton
public class WifiRepository {

    private static final String TAG = "WifiRepository";

    public Context context;

    private WifiManager wifiManager;

    public SingleLiveEvent<List<ScanResult>> scanLiveEvent = new SingleLiveEvent<>();


    @Inject
    public WifiRepository(Context context) {
        this.context = context;
        wifiManager = (WifiManager) context.getApplicationContext().
                getSystemService(Context.WIFI_SERVICE);
    }

    private boolean checkWifiScan() {
        if (!wifiManager.isWifiEnabled()) {
            Log.e(TAG,"yaha se");
            Toast.makeText(context, "Wifi is not enable. Turning on wifi",
                    Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }
        return wifiManager.startScan();
    }
   /*
   **  getWifiList will the available wifi list after scanning and assigning to live data for
   * observing in activity/fragment
   * */
    public SingleLiveEvent<List<ScanResult>> getWifiList() {
        Log.e(TAG, "getWifiList " + checkWifiScan());
        scanLiveEvent.setValue(getWifiScanResult());
        return scanLiveEvent;
    }

    private List<ScanResult> getWifiScanResult() {
        return wifiManager.getScanResults();
    }

    /**
     * use for connecting to wifi network
     * parameters
     * networkSSID: wifi name
     * networkPassword;: password for wifi network
     * isOpenWifi: to check for open wifi network
     * */
    public boolean connectToWifi(String networkSSID, String networkPassword, boolean isOpenWifi) {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = String.format("\"%s\"", networkSSID);
        if(isOpenWifi){
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else {
            conf.preSharedKey = String.format("\"%s\"", networkPassword);
        }

        int netId = wifiManager.addNetwork(conf);
        Log.e(TAG,"connectToWifi called netId " + netId + " networkPassword " +
                networkPassword + " isOpenWifi " + isOpenWifi);

        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);

        return wifiManager.reconnect();
    }

    /**
     * use for disconnecting wifi network
     * */
    public void disConnect(String wifiName) {
        Log.e(TAG, "disConnect wifiName " + wifiName);
        wifiManager.disconnect();
    }

    /**
     * use for updating password of wifi network
     * parameters
     * networkSSID: wifi name
     * networkPassword;: password for wifi network
     * */
    public boolean changePassword(String ssid, String password) {
        WifiConfiguration wifiConf = null;
        //existing configured networks
        Log.e(TAG,"changePassword limit " + ssid);

        wifiConf = new WifiConfiguration();

        wifiConf.SSID = String.format("\"%s\"", ssid);
        wifiConf.preSharedKey = String.format("\"%s\"", password);

        int netId = wifiManager.updateNetwork(wifiConf);
        Log.d(TAG, " changePassword configuration created " + netId);

        wifiManager.saveConfiguration();
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);

        return wifiManager.reconnect();
    }


    
}
