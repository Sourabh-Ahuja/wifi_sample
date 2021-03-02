package com.sourabh.openapp.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import dagger.android.support.AndroidSupportInjection;


public abstract class BaseFragment<VM extends BaseViewModel, Binding extends ViewDataBinding>
        extends Fragment {

    private static final String TAG = "BaseFragment";


    public BaseActivity baseActivity;

    protected VM viewModel;

    protected Binding dataBinding;

    protected abstract VM getViewModel();

    protected abstract int getLayoutResource();

    public abstract void initObservers();

    public abstract void setUp(View view);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        performDependencyInjection();
        super.onCreate(savedInstanceState);

        setUpViewModel();
        setUpToastMessage();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        dataBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers();
        setUp(view);
    }

    private void setUpViewModel() {
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
    }

    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

    private void setUpToastMessage() {
        viewModel.getToastMessageLiveEvent().observe(this, (Observer<String>) toastMessage -> {
            if (baseActivity != null)
                baseActivity.showToast(toastMessage);
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.baseActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        baseActivity = null;
        super.onDetach();
    }

}
