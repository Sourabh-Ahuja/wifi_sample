package com.sourabh.openapp.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.List;

import javax.inject.Inject;

// not using this class migth delete this later
public class WifiConnectionReceiver extends BroadcastReceiver {

    private static final String TAG = "WifiConnectionReceiver";

    /**
     * Notifies the receiver to turn wifi on
     */
    private static final String ACTION_WIFI_ON = "android.intent.action.WIFI_ON";

    /**
     * Notifies the receiver to turn wifi off
     */
    private static final String ACTION_WIFI_OFF = "android.intent.action.WIFI_OFF";

    /**
     * Notifies the receiver to connect to a specified wifi
     */
    private static final String ACTION_CONNECT_TO_WIFI = "android.intent.action.CONNECT_TO_WIFI";

    private WifiManager wifiManager;

    public WifiConnectionReceiver() {
    }

    public void onReceive(Context c, Intent intent) {
        Log.d(TAG, "onReceive() called with: intent = [" + intent + "]");

        wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);

        final String action = intent.getAction();

        if (!isTextNullOrEmpty(action)) {
            switch (action) {
                case ACTION_WIFI_ON:
                    // Turns wifi on
                    wifiManager.setWifiEnabled(true);
                    break;
                case ACTION_WIFI_OFF:
                    // Turns wifi off
                    wifiManager.setWifiEnabled(false);
                    break;
                case ACTION_CONNECT_TO_WIFI:
                    // Connects to a specific wifi network
                    final String networkSSID = intent.getStringExtra("ssid");
                    final String networkPassword = intent.getStringExtra("password");

                    if (!isTextNullOrEmpty(networkSSID) && !isTextNullOrEmpty(networkPassword)) {
                        connectToWifi(networkSSID, networkPassword);
                    } else {
                        Log.e(TAG, "onReceive: cannot use " + ACTION_CONNECT_TO_WIFI +
                                "without passing in a proper wifi SSID and password.");
                    }
                    break;
            }
        }
    }

    private boolean isTextNullOrEmpty(final String text) {
        return text != null && !text.isEmpty();
    }

    /**
     * Connect to the specified wifi network.
     *
     * @param networkSSID     - The wifi network SSID
     * @param networkPassword - the wifi password
     */
    private void connectToWifi(final String networkSSID, final String networkPassword) {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = String.format("\"%s\"", networkSSID);
        conf.preSharedKey = String.format("\"%s\"", networkPassword);

        int netId = wifiManager.addNetwork(conf);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
    }

    @NonNull
    public static IntentFilter getIntentFilterForWifiConnectionReceiver() {
        final IntentFilter randomIntentFilter = new IntentFilter(ACTION_WIFI_ON);
        randomIntentFilter.addAction(ACTION_WIFI_OFF);
        randomIntentFilter.addAction(ACTION_CONNECT_TO_WIFI);
        return randomIntentFilter;
    }

//    public boolean changePassword(String ssid, String password) {
//        WifiConfiguration wifiConf = null;
//        WifiConfiguration savedConf = null;
//
//        //existing configured networks
//        Log.e(TAG,"changePassword limit " + ssid);
//
//        if (ActivityCompat.checkSelfPermission(context,
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return false;
//        }
//        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
//
//        if(list!=null) {
//            for( WifiConfiguration i : list ) {
//                if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
//                    savedConf = i;
//                    break;
//                }
//            }
//        }
//
//        if(savedConf!=null) {
//            wifiConf = savedConf;
//        } else {
//            wifiConf = new WifiConfiguration();
//        }
//
//        wifiConf.SSID = String.format("\"%s\"", ssid);
//        wifiConf.preSharedKey = String.format("\"%s\"", password);
//
//        int netId;
//
//        if(savedConf!=null) {
//            netId = wifiManager.updateNetwork(wifiConf);
//            Log.d(TAG, "changePassword configuration updated " + netId);
//        } else {
//            netId = wifiManager.addNetwork(wifiConf);
//            Log.d(TAG, " changePassword configuration created " + netId);
//        }
//
//        wifiManager.saveConfiguration();
//        wifiManager.disconnect();
//        wifiManager.enableNetwork(netId, true);
//
//        return wifiManager.reconnect();
//    }

}
