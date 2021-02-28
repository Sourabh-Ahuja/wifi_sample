package com.sourabh.openapp.ui.main;


import android.widget.ImageView;

import com.sourabh.openapp.model.Wifi;

public interface FragmentCommunicationListener {

    void onWifiClicked(Wifi wifi);

    void onBackButtonClick();
}
