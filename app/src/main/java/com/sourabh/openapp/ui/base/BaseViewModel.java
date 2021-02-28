package com.sourabh.openapp.ui.base;


import androidx.lifecycle.ViewModel;


import com.sourabh.openapp.repo.WifiRepository;
import com.sourabh.openapp.utils.SchedulerProvider;
import com.sourabh.openapp.utils.ToastMessageLiveEvent;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseViewModel extends ViewModel {

    private final ToastMessageLiveEvent toastMessageLiveEvent = new ToastMessageLiveEvent();

    public SchedulerProvider schedulerProvider;
    public WifiRepository wifiRepository;
    private CompositeDisposable compositeDisposable;


    public BaseViewModel(WifiRepository wifiRepository, SchedulerProvider schedulerProvider) {
        this.schedulerProvider = schedulerProvider;
        this.wifiRepository = wifiRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public ToastMessageLiveEvent getToastMessageLiveEvent() {
        return toastMessageLiveEvent;
    }

    public void showToastMessage(String toastMessage) {
        toastMessageLiveEvent.setValue(toastMessage);
    }

    @Override
    protected void onCleared() {
        try {
            compositeDisposable.dispose();
            super.onCleared();
        } catch (Exception e) {
            //do nothing
        }
    }

}
