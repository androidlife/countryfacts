package com.countryfacts.screen.home;

import android.app.ActionBar;

import com.countryfacts.model.Country;
import com.countryfacts.model.Error;

/**
 * All the related MVC interface interaction
 */

public interface HomeContracts {
    //this will be implemented by our Activity
    interface Controller {
        ActionBar getActionBar();

        void onRetry();

        void onRefresh();
    }

    //this will be implemented by our ViewGroup
    interface View {

        void showError(boolean show);

        void showProgress(boolean show);

        void showInfo(String info);

        void populateList(Country country);

        int getState();

        void setState(int state);

        void scrollToTop();

        int STATE_ERROR = 0x1, STATE_EMPTY = 0x2, STATE_LOADING = 0x3, STATE_LOADED = 0x4;
    }

    //this will be implemented by our Model layer
    interface Model {
        void fetchCountryInfo(CountryInfoFetchListener infoFetchListener);

        void cancel(boolean cancel);
    }

    interface CountryInfoFetchListener {
        void onSuccess(Country country);

        void onError(Error error);
    }
}
