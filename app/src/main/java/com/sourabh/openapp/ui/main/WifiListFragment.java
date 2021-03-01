package com.sourabh.openapp.ui.main;


import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;

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

        wifiViewModel.observeFromDb().observe(this, aBoolean -> {
             if(!aBoolean){
                 if (fragmentCommunicationListener != null) {
                     fragmentCommunicationListener.onWifiClicked(wifiViewModel.
                             getWifiLiveData().getValue());
                 }
             }
        });

        wifiViewModel.observeResult().observe(this, aBoolean -> {
            Log.e(TAG,"initObservers " + aBoolean);
            if(aBoolean){
                baseActivity.showToast("Connected to wifi from list fragment");
            } else {
                baseActivity.showToast("Something went wrong please try again");
            }
        });

        wifiViewModel.getWifiList().observe(this, scanResults -> {
               ArrayList<Wifi> wifiArrayList = new ArrayList<>();
                for(ScanResult scanResult : scanResults){
                    Wifi wifi = new Wifi(scanResult.SSID);
                    int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
                    wifi.setSignalStrength(String.valueOf(level));
                    wifiArrayList.add(wifi);
                    Log.e(TAG, "wifi name " + scanResult.SSID + " signal speed "
                     + level);
                }
                inItRecyclerView(wifiArrayList);
        });

    }

    @Override
    public void setUp(View view) {
        Log.e(TAG,"Wifi list fragment loaded");

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
        viewModel.fetchDataFromDb(wifi);
    }

    @Override
    public void onSubmitButtonClick(String wifiName, String password) {

    }

}
