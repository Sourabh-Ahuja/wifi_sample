package com.sourabh.openapp;

import android.Manifest;

public class AppConstants {

    public static final int APP_DB_VERSION = 1;
    public static String BUNDLE_WIFI = "bundlewifi";
    public static String BUNDLE_WIFI_LIST = "bundlewifilist";

    public static String[] PERMISSIONS_WIFI = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE};

    public static final int REQUEST_WIFI_PERMISSION = 1;

}
