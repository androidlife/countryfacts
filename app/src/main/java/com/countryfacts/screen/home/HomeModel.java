package com.countryfacts.screen.home;

import com.countryfacts.model.Country;
import com.countryfacts.model.Error;
import com.countryfacts.network.provider.retrofit.ApiManager;
import com.countryfacts.utils.GeneralUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Our model part which is responsible for fetching the data from API
 */

public class HomeModel implements HomeInteraction.Model {

    private boolean isCancelled;
    private Disposable callback;

    @Override
    public void fetchCountryInfo(HomeInteraction.CountryInfoFetchListener infoFetchListener) {
        if (!GeneralUtil.isConnectedToNetwork()) {
            onFailure(new Error(Error.Type.Network), infoFetchListener);
            return;
        }
        unsubscribeRemoteCallback();
        callback = ApiManager.getApiService().getCountryInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(c -> onSuccess(c, infoFetchListener),
                        e -> {
                            Timber.e("Error fetching Country Info due to: %s", e.getMessage());
                            onFailure(new Error(Error.Type.Fetch), infoFetchListener);
                        });
    }

    @Override
    public void cancel(boolean cancel) {
        this.isCancelled = cancel;
        if (cancel)
            unsubscribeRemoteCallback();
    }

    private void unsubscribeRemoteCallback() {
        if (callback != null) {
            callback.dispose();
            callback = null;
        }
    }

    private void onSuccess(Country country, HomeInteraction.CountryInfoFetchListener infoFetchListener) {
        Timber.d("Successfully fetched country info");
        if (infoFetchListener == null || isCancelled)
            return;
        infoFetchListener.onSuccess(country);
    }

    private void onFailure(Error error, HomeInteraction.CountryInfoFetchListener infoFetchListener) {
        if (infoFetchListener == null || isCancelled)
            return;
        infoFetchListener.onError(error);
    }
}
