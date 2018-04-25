package com.countryfacts.screen.home.utils;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;

/**
 */

public class CustomIdlingResource implements IdlingResource {

    private final AtomicBoolean idleNow = new AtomicBoolean(false);
    private volatile ResourceCallback callback;


    @Override
    public String getName() {
        return CustomIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return idleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    private void setIdle() {
        idleNow.set(true);
        if (callback != null)
            callback.onTransitionToIdle();
    }

    public void startCountdown(long countDown, TimeUnit timeUnit) {
        Observable.timer(countDown, timeUnit).subscribe(l -> setIdle());
    }


}
