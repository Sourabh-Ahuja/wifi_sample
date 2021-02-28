package com.sourabh.openapp.ui.main;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sourabh.openapp.R;
import com.sourabh.openapp.databinding.WifiItemLayoutBinding;
import com.sourabh.openapp.model.Wifi;
import com.sourabh.openapp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.WifiViewHolder> {

    private static final String TAG = "WifiListAdapter";

    @Inject
    Context context;

    public interface WifiItemClickListener {
        void onWifiItemClick(Wifi movie);

        void onSubmitButtonClick(String wifiName, String password);
    }

    private List<Wifi> wifiArrayList = new ArrayList<>();
    private WifiItemClickListener wifiItemClickListener;
    private BaseActivity baseActivity;

    public void setBaseActivity(BaseActivity activity) {
        baseActivity = activity;
    }


    public WifiListAdapter() { }

    public void addData(List<Wifi> wifiArrayList) {
        //  this.movieList.clear();
        Log.e(TAG," movieList after clear" + this.wifiArrayList.size());
        Log.e(TAG," movieList para" + wifiArrayList.size());
        this.wifiArrayList.addAll(wifiArrayList);
        Log.e(TAG," movieList " + this.wifiArrayList.size());

        notifyDataSetChanged();
    }

    public void clearList(boolean shouldClear) {
        if (!wifiArrayList.isEmpty() && shouldClear)
            wifiArrayList.clear();
    }

    public void setWifiItemClickListener(WifiItemClickListener wifiItemClickListener) {
        this.wifiItemClickListener = wifiItemClickListener;
    }

    @NonNull
    @Override
    public WifiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        WifiItemLayoutBinding movieItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()), R.layout.wifi_item_layout, viewGroup, false);
        return new WifiViewHolder(movieItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WifiViewHolder movieItemViewHolder, int i) {
        if(baseActivity != null){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            baseActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            movieItemViewHolder.wifiItemLayoutBinding.containerItem.getLayoutParams().height = height / 10;
        }

        movieItemViewHolder.bindData(wifiArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return wifiArrayList == null ? 0 : wifiArrayList.size();
    }

    public class WifiViewHolder extends RecyclerView.ViewHolder {

        WifiItemLayoutBinding wifiItemLayoutBinding;

        public WifiViewHolder(@NonNull WifiItemLayoutBinding wifiItemLayoutBinding) {
            super(wifiItemLayoutBinding.getRoot());
            this.wifiItemLayoutBinding = wifiItemLayoutBinding;
        }

        public void bindData(Wifi wifi) {
            wifiItemLayoutBinding.setWifi(wifi);
            wifiItemLayoutBinding.setWifiClickListener(wifiItemClickListener);
            wifiItemLayoutBinding.containerItem.setOnClickListener(v -> {
                wifiItemClickListener.onWifiItemClick(wifi);
            });
        }
    }

}
