package com.sourabh.openapp.ui.main;

import android.os.Bundle;
import android.view.View;

import com.sourabh.openapp.AppConstants;
import com.sourabh.openapp.R;
import com.sourabh.openapp.databinding.PasswordFrgamentBinding;
import com.sourabh.openapp.model.Wifi;
import com.sourabh.openapp.ui.base.BaseFragment;

import javax.inject.Inject;

public class PasswordFragment extends BaseFragment<WifiViewModel, PasswordFrgamentBinding> {

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

    }

    @Override
    public void setUp(View view) {

//        if (getArguments() != null) {
//            Wifi news = getArguments().getParcelable(AppConstants.BUNDLE_WIFI);
//            dataBinding.setNews(news);
//            dataBinding.date.setText(CommonUtils.dateToString(news.getPublishedAt()));
//        }
//
//        dataBinding.backButtonIv.setOnClickListener(v -> {
//
//            if (fragmentCommunicationListener != null)
//                fragmentCommunicationListener.onBackButtonClick();
//        });

    }
}
