package com.sourabh.openapp.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;

import com.sourabh.openapp.AppConstants;
import com.sourabh.openapp.R;
import com.sourabh.openapp.databinding.PasswordFrgamentBinding;
import com.sourabh.openapp.model.Wifi;
import com.sourabh.openapp.ui.base.BaseFragment;

import javax.inject.Inject;

public class PasswordFragment extends BaseFragment<WifiViewModel, PasswordFrgamentBinding>
        implements WifiListAdapter.WifiItemClickListener {

    private static final String TAG = "PasswordFragment";

    @Inject
    WifiViewModel wifiViewModel;

    private FragmentCommunicationListener fragmentCommunicationListener;

    public static PasswordFragment newInstance(Wifi wifi) {
        PasswordFragment passwordFragment = new PasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.BUNDLE_WIFI, wifi);
        passwordFragment.setArguments(bundle);
        return passwordFragment;
    }

    public void setListener(FragmentCommunicationListener fragmentCommunicationListener) {
        this.fragmentCommunicationListener = fragmentCommunicationListener;
    }


    @Override
    protected WifiViewModel getViewModel() {
        return wifiViewModel;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.password_frgament;
    }

    @Override
    public void initObservers() {
        wifiViewModel.observeResult().observe(this, aBoolean -> {
            Log.e(TAG,"initObservers " + aBoolean);
            if(aBoolean){
                baseActivity.showToast("Connected to wifi");
                baseActivity.onBackPressed();
            } else {
                baseActivity.showToast("Something went wrong please try again");
            }
        });
    }

    @Override
    public void setUp(View view) {

        if (getArguments() != null) {
            Wifi wifi = getArguments().getParcelable(AppConstants.BUNDLE_WIFI);
            dataBinding.setWifi(wifi);
            dataBinding.setWifiClickListener(this);

            dataBinding.submitButton.setOnClickListener(v -> {
                if(!TextUtils.isEmpty(dataBinding.passwordField.getText().toString())){
                    if(dataBinding.passwordField.getText().toString().length() >= 8){
                        viewModel.connectToWifi(wifi.getWifiName(),
                                dataBinding.passwordField.getText().toString());
                    } else {
                        baseActivity.showToast("Password must be greater than " +
                                "or equal to 8 characters");
                    }
                } else {
                    baseActivity.showToast("Password can not be null");
                }
            });
        }

    }

    @Override
    public void onWifiItemClick(Wifi movie) {

    }

    @Override
    public void onSubmitButtonClick(String wifiName, String password) {

    }
}
