package com.countryfacts.screen.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.countryfacts.R;
import com.countryfacts.base.BaseActivity;
import com.countryfacts.model.Country;
import com.countryfacts.model.Error;

import butterknife.OnClick;
import timber.log.Timber;

public class HomeActivity extends BaseActivity implements HomeInteraction.Controller {


    HomeView homeView;
    HomeModel homeModel;
    Country country;

    private static final String DATA_COUNTRY = "Country";
    private static final String VIEW_STATE = "ViewState";
    private int viewState = HomeView.STATE_EMPTY;

    @Override
    protected void onPermissionChecked() {
        setContentView(R.layout.activity_home);
        homeView = new HomeView(findViewById(R.id.swipe_ref_layout), this);

        homeModel = new HomeModel();
        if (isCountryValid()) {
            onDataFetched(country);
            return;
        }
        setViewState(viewState);
        fetchData();
    }

    private void setViewState(int state) {
        homeView.setState(state);
    }

    @Override
    protected void onPause() {
        super.onPause();
        homeModel.cancel(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        homeModel.cancel(false);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isCountryValid())
            outState.putParcelable(DATA_COUNTRY, country);
        outState.putInt(VIEW_STATE, homeView.getState());

    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            return;
        if (savedInstanceState.containsKey(DATA_COUNTRY))
            country = savedInstanceState.getParcelable(DATA_COUNTRY);
        if (savedInstanceState.containsKey(VIEW_STATE))
            viewState = savedInstanceState.getInt(VIEW_STATE);

    }

    private boolean isCountryValid() {
        return country != null && country.isValid();
    }

    private void fetchData() {
        homeView.showProgress(true);
        homeModel.fetchCountryInfo(new HomeInteraction.CountryInfoFetchListener() {
            @Override
            public void onSuccess(Country country) {
                Timber.d("Country fetched = %s", country.name);
                onDataFetched(country);
            }

            @Override
            public void onError(Error error) {
                Timber.e("Error fetching country info");
                //TODO check proper state
                homeView.showInfo(getErrorMessage(error));
                homeView.showProgress(false);
                homeView.showError(true);
            }
        });
    }

    private void onDataFetched(Country country) {
        this.country = country;
        homeView.showProgress(false);
        homeView.showData(country);
    }

    @Override
    public void onReload() {
        fetchData();
    }



    private String getErrorMessage(Error error) {
        switch (error.type) {
            case Network:
                return getString(R.string.error_network);
            default:
                return getString(R.string.error_fetch);

        }
    }
}
