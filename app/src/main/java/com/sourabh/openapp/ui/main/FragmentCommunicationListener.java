package com.sourabh.openapp.ui.main;


import android.widget.ImageView;

import com.sourabh.openapp.model.Wifi;

/**
 * listener for changing the fragment
 * */
public interface FragmentCommunicationListener {

    void onWifiClicked(Wifi wifi, boolean changePassword);

    void onBackButtonClick();
}
