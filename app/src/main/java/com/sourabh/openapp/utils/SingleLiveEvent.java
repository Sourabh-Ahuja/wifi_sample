package com.sourabh.openapp.utils;


import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private final static String TAG = "SingleLiveEvent";

    private final AtomicBoolean pendingEvent = new AtomicBoolean(false);

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<? super T> observer) {

        if (hasActiveObservers()) {
            Log.d(TAG, "Multiple observers registered but only one will be notified of changes.");
        }
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                if (pendingEvent.weakCompareAndSet(true, false)) ;
                observer.onChanged(t);
            }
        });
    }

    // MutableLiveData method
    @Override
    public void setValue(T value) {
        pendingEvent.set(true);
        super.setValue(value);  //Setting value from the view model
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        setValue(null);
    }
}

