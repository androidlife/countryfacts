package com.countryfacts.screen.home.utils;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;

/**
 * An IdlingResource, which will be idle after the given time is elapsed
 * Specially needed to check for network timeout as any network call
 * requires some time. So this IdlingResource is to be used, if our test
 * contains networking fetching tasks
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
    //method to
    public void startCountdown(long countDown, TimeUnit timeUnit) {
        Observable.timer(countDown, timeUnit).subscribe(l -> setIdle());
    }


}
