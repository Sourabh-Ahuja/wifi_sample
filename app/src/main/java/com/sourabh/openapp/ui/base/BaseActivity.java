package com.sourabh.openapp.ui.base;


import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<Binding extends ViewDataBinding, VM extends BaseViewModel>
        extends DaggerAppCompatActivity {


    /**
     * ViewModel
     */
    protected VM viewModel;


    /**
     * Binding Layout
     */
    public Binding dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        performDependencyInjection();
        setUpViewModel();
        setUpToastMessage();

        dataBinding = DataBindingUtil.setContentView(this, getLayoutResource());
        initObservers();
        setUp();
    }

    private void performDependencyInjection() {

        AndroidInjection.inject(this);
    }

    private void setUpViewModel() {

        //        Both way of writing this are fine

        if (viewModel == null)
            this.viewModel = getViewModel();

        //        this.viewModel = viewModel == null ? getViewModel() : viewModel;
    }


    private void setUpToastMessage() {
        viewModel.getToastMessageLiveEvent().observe(this, (Observer<String>) this::showToast);
    }

    public void showToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void onFragmentAttached() {
        //do nothing
    }

    /**
     * @return iewModel instance
     * @Override for set view model
     */

    protected abstract int getLayoutResource();

    protected abstract VM getViewModel();

    protected abstract void initObservers();

    protected abstract void setUp();

}
