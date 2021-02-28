package com.sourabh.openapp.utils;


import androidx.lifecycle.LifecycleOwner;

/**
 * <p>
 * A SingleLiveEvent used for Toast messages. Like a {@link SingleLiveEvent} but also prevents
 * null messages and uses a custom observer.
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
public class ToastMessageLiveEvent extends SingleLiveEvent<String> {

    public void observe(LifecycleOwner owner, final ToastMessageObserver toastMessageObserver) {
        super.observe(owner, s -> {
            if (s == null) {
                return;
            }
            toastMessageObserver.onNewMessage(s);
        });
    }

    public interface ToastMessageObserver {

        /**
         * Called when there is a new message to be shown.
         *
         * @param toastMessage The new message, non-null.
         */

        void onNewMessage(String toastMessage);
    }
}
