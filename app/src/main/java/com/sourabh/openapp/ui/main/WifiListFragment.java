package com.sourabh.openapp.ui.main;

import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

    private static final String TAG = "HomeFragment";
    String selectedOrder = "Date";
    int checkedItem = 0;

    @Inject
    WifiViewModel wifiViewModel;

    @Inject
    WifiListAdapter movieListAdapter;

    private FragmentCommunicationListener fragmentCommunicationListener;

    private List<Wifi> wifiArrayList = new ArrayList<>();
    private boolean isCallFromNetwork = false;


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

//        wifiViewModel.getMovieList().observe(this,
//                entityMovies -> inItRecyclerView(entityMovies));
//        Log.e(TAG,"initObservers");

    }

    @Override
    public void setUp(View view) {

//        if (wifiArrayList == null || wifiArrayList.isEmpty()) {
//            isCallFromNetwork = true;
//            dataBinding.setShouldShowLoadingBar(true);
//            newsViewModel.fetchMovieList();
//        } else {
//            isCallFromNetwork = false;
//        }
//        dataBinding.sort.setOnClickListener(v -> {
//            showAlertDialog(movieList);
//        });
    }

    private void inItRecyclerView(List<Wifi> movieList) {

        this.wifiArrayList.clear();
        this.wifiArrayList.addAll(Objects.requireNonNull(movieList));

//        dataBinding.movieListRv.setHasFixedSize(true);
//        dataBinding.setShouldShowLoadingBar(false);
//        dataBinding.movieListRv.setLayoutManager(
//                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
//        dataBinding.movieListRv.setAdapter(movieListAdapter);
//        movieListAdapter.clearList(!isCallFromNetwork);
//        movieListAdapter.addData(movieList);
//        movieListAdapter.setBaseActivity(baseActivity);
//        movieListAdapter.setMovieClickListener(this);
    }

    @Override
    public void onWifiItemClick(Wifi wifi) {
        if (fragmentCommunicationListener != null) {
            fragmentCommunicationListener.onWifiClicked(wifi);
        }
    }
}
