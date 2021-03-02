package com.sourabh.openapp.ui.main;


import android.app.AlertDialog;
import android.content.res.Configuration;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sourabh.openapp.R;
import com.sourabh.openapp.databinding.WifiListFragmentBinding;
import com.sourabh.openapp.model.Wifi;
import com.sourabh.openapp.ui.base.BaseFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class WifiListFragment extends BaseFragment<WifiViewModel, WifiListFragmentBinding>
        implements WifiListAdapter.WifiItemClickListener {

    private static final String TAG = "WifiListFragment";

    String selectedOrder = "DisConnect";
    int checkedItem = 0;
    private static final String DISCONNECT = "DisConnect";
    private static final String CHANGE_PASSWORD = "Change Password";
    String theme = "";


    @Inject
    WifiViewModel wifiViewModel;

    @Inject
    WifiListAdapter movieListAdapter;

    private FragmentCommunicationListener fragmentCommunicationListener;

    public static WifiListFragment newInstance() {
        return new WifiListFragment();
    }

    public void setFragmentCommunicationListener(FragmentCommunicationListener fragmentCommunicationListener) {
        this.fragmentCommunicationListener = fragmentCommunicationListener;
    }

    @Override
    protected WifiViewModel getViewModel() {
        return wifiViewModel;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.wifi_list_fragment;
    }

    @Override
    public void initObservers() {

        wifiViewModel.observeFromDb().observe(getViewLifecycleOwner(), aBoolean -> {
            if(getViewLifecycleOwner().getLifecycle().getCurrentState()== Lifecycle.State.RESUMED){
                if(!aBoolean){
                    if (fragmentCommunicationListener != null) {
                        Log.e(TAG,"yahi se fragment call ho raha");
                        fragmentCommunicationListener.onWifiClicked(wifiViewModel.
                                getWifiLiveData().getValue(),false);
                    }
                }
            }
        });

        wifiViewModel.observeResult().observe(getViewLifecycleOwner(), aBoolean -> {
            Log.e(TAG,"initObservers " + aBoolean);
            if(getViewLifecycleOwner().getLifecycle().getCurrentState()== Lifecycle.State.RESUMED) {
                if (aBoolean) {
                    baseActivity.showToast("Connected to wifi from list fragment");
                } else {
                    baseActivity.showToast("Something went wrong please try again");
                }
            }
        });

        wifiViewModel.observeList().observe(this, scanResults -> {
               ArrayList<Wifi> wifiArrayList = new ArrayList<>();
                for(ScanResult scanResult : scanResults){
                    Wifi wifi = new Wifi(scanResult.SSID);
                    int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
                    wifi.setSignalStrength(String.valueOf(level));
                    String capabilities = scanResult.capabilities;
                    Log.e(TAG, "wifi name " + scanResult.SSID + " signal speed "
                            + level+ " capabilities : " + capabilities);
                    if (capabilities.toUpperCase().contains("WEP")) {
                        // WEP Network
                        wifi.setOpenNetwork(false);
                    } else wifi.setOpenNetwork(!capabilities.toUpperCase().contains("WPA")
                            && !capabilities.toUpperCase().contains("WPA2"));
                    wifiArrayList.add(wifi);
                }
                inItRecyclerView(wifiArrayList);
        });

    }

    @Override
    public void setUp(View view) {
        Log.e(TAG,"Wifi list fragment loaded");

        dataBinding.theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(theme.equalsIgnoreCase("Day Theme")){
                    theme = "Night Theme";
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    dataBinding.theme.setText(theme);
                } else if(theme.equalsIgnoreCase("Night Theme")){
                    theme = "Day Theme";
                    dataBinding.theme.setText(theme);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        viewModel.getWifiList();
          getWifiList();
    }

    private void checkDefaultDisplayMode() {
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                //   dataBinding.switchTheme.setChecked(false);
                theme ="Day Theme";
                break;
            // Night mode is not active, we're in day time
            case Configuration.UI_MODE_NIGHT_YES:
                //    dataBinding.switchTheme.setChecked(true);
                theme ="Night Theme";
                break;
            // Night mode is active, we're at night!
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                // We don't know what mode we're in, assume notnight
                //     dataBinding.switchTheme.setChecked(false);
                theme ="Day Theme";
                break;

        }

        dataBinding.theme.setText(theme);
    }

    private void refreshList() {
        viewModel.getWifiList();
    }

    private void getWifiList() {
        refreshList();

        Handler handler = new Handler();
        handler.postDelayed(this::getWifiList,5000);

    }

    private void inItRecyclerView(List<Wifi> wifiList) {
        Log.e(TAG,"inItRecyclerView " + wifiList);
        dataBinding.wifiRecycler.setHasFixedSize(true);
        dataBinding.wifiRecycler.setLayoutManager(
                new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.VERTICAL,false));
        dataBinding.wifiRecycler.setAdapter(movieListAdapter);
        movieListAdapter.addData(wifiList);
        movieListAdapter.setBaseActivity(baseActivity);
        movieListAdapter.setWifiItemClickListener(this);
    }

    @Override
    public void onWifiItemClick(Wifi wifi) {
        Log.e(TAG,"onWifiItemClick " + wifi.isOpenNetwork());
        if(wifi.isOpenNetwork()){
            viewModel.connectToWifi(wifi.getWifiName(),null,true);
        } else {
            viewModel.fetchDataFromDb(wifi);
        }
    }

    @Override
    public void onMenuButtonClicked(String wifiName, int position) {
       showAlertDialog(wifiName);
    }

    private void showAlertDialog(String wifiName) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(baseActivity);
        alertDialog.setTitle("Sort movies by");
        String[] items = {DISCONNECT,CHANGE_PASSWORD};
        if(selectedOrder.equalsIgnoreCase("DisConnect")){
            checkedItem = 0;
        } else {
            checkedItem = 1;
        }
        alertDialog.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
            switch (which) {
                case 0:
                    selectedOrder = DISCONNECT;
                    break;
                case 1:
                    selectedOrder = CHANGE_PASSWORD;
                    break;
            }
        });

        alertDialog.setPositiveButton("Ok", (dialog, which) -> {
            Log.e(TAG,"selectedOrder " + selectedOrder);
            if(selectedOrder.equalsIgnoreCase(DISCONNECT)){
                movieListAdapter.clearList(true);
                viewModel.disConnect(wifiName);
            } else if(selectedOrder.equalsIgnoreCase(CHANGE_PASSWORD)){
                fragmentCommunicationListener.onWifiClicked(wifiViewModel.
                        getWifiLiveData().getValue(),true);
            }
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkDefaultDisplayMode();
    }
}
