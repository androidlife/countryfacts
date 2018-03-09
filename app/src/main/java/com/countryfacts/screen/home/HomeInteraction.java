package com.countryfacts.screen.home;

import android.app.ActionBar;
import android.widget.Toolbar;

import com.countryfacts.model.Country;
import com.countryfacts.model.Error;

/**
 */

public interface HomeInteraction {
    interface Controller {
        ActionBar getActionBar();

        void onReload();
    }

    interface View {

        void showError(boolean show);

        void showProgress(boolean show);

        void showInfo(String info);

        void showData(Country country);

        int getState();

        void setState(int state);

        int STATE_ERROR = 0x1, STATE_EMPTY = 0x2, STATE_LOADING = 0x3;
    }

    interface Model {
        void fetchCountryInfo(CountryInfoFetchListener infoFetchListener);

        void cancel(boolean cancel);
    }

    interface CountryInfoFetchListener {
        void onSuccess(Country country);

        void onError(Error error);
    }
}
