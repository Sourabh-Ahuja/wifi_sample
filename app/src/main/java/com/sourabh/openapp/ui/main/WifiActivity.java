package com.sourabh.openapp.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sourabh.openapp.R;
import com.sourabh.openapp.databinding.ActivityWifiBinding;
import com.sourabh.openapp.model.Wifi;
import com.sourabh.openapp.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

import static com.sourabh.openapp.AppConstants.PERMISSIONS_WIFI;
import static com.sourabh.openapp.AppConstants.REQUEST_WIFI_PERMISSION;

public class WifiActivity extends BaseActivity<ActivityWifiBinding,WifiViewModel>
        implements FragmentCommunicationListener {

    private static final String TAG = "WifiActivity";
    boolean permissionGranted = false;
    public static final String WIFI_LIST_FRAGMENT = "wifiListFragment";
    public static final String PASSWORD_FRAGMENT = "newsDetailFragment";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    WifiViewModel wifiViewModel;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_wifi;
    }

    @Override
    protected WifiViewModel getViewModel() {
        return wifiViewModel;
    }

    @Override
    protected void initObservers() {

    }

    @Override
    protected void setUp() {
        if(!checkAppPermission()){
            showAlertDialog("Alert","Application needs access to the wifi " +
                    "to connect with the wifi.");
        } else {
            loadWifiListFragment();
        }
    }

    private void loadWifiListFragment() {
        WifiListFragment homeFragment = WifiListFragment.newInstance();
        homeFragment.setFragmentCommunicationListener(this);

        loadFragment(homeFragment, WIFI_LIST_FRAGMENT);
    }

    private boolean checkAppPermission() {

        boolean  permissionGranted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        return permissionGranted;
    }

    private void loadFragment(Fragment fragment, String tag) {

        /**
         *  #getSupportFragmentManager().beginTransaction  -    //create a transaction for transition here
         *  #replace() -     // put the fragment in place
         *  #addToBackStack() -     // this is the part that will cause a fragment to be added to backstack,
         *                          // this way we can return to it at any time using this tag
         *
         */
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WIFI_PERMISSION) {
            if (grantResults.length > 0) {
                if(permissions.length == grantResults.length){
                    permissionGranted = true;
                    loadWifiListFragment();
                } else {
                    Log.e(TAG,"Permission denied 1 " + grantResults.toString());
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    permissionGranted = true;
//                } else {
//                    Log.e(TAG,"Permission denied 1");
//                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
//                }
            } else {
                showAlertDialog("Alert","Application needs access to the wifi " +
                        "to connect with the wifi.");
            }
        }
    }

    public void showAlertDialog(String title, String mesage) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WifiActivity.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(mesage);
        String[] items = {"Date","Rating"};

        alertDialog.setPositiveButton("Ok", (dialog, which) -> {
            ActivityCompat.requestPermissions(this, PERMISSIONS_WIFI,
                    REQUEST_WIFI_PERMISSION);
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    @Override
    public void onWifiClicked(Wifi wifi) {

    }

    @Override
    public void onBackButtonClick() {

    }
}