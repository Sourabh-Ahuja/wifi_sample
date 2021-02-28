package com.sourabh.openapp.ui.main;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class WifiListFragmentModule {

    @Provides
    WifiListAdapter provideHomeFragmentMovieListAdapter(WifiListFragment wifiListFragment) {
        return new WifiListAdapter();
    }

    @Provides
    LinearLayoutManager provideGridLayoutManager(WifiListFragment newsListFragment) {
        return new GridLayoutManager(newsListFragment.getContext(), 2,
                LinearLayoutManager.VERTICAL, false);
    }

}
