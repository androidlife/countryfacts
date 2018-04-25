package com.countryfacts.screen.home;

import android.os.Bundle;

import com.countryfacts.R;
import com.countryfacts.base.BaseActivity;
import com.countryfacts.model.Country;
import com.countryfacts.model.Error;

import timber.log.Timber;

public class HomeActivity extends BaseActivity implements HomeInteraction.Controller {


    private HomeView homeView;
    private HomeModel homeModel;
    private Country country;

    private static final String DATA_COUNTRY = "Country";
    private static final String VIEW_STATE = "ViewState";
    private int viewState = HomeView.STATE_EMPTY;

    @Override
    protected void onPermissionChecked() {
        setContentView(R.layout.activity_home);
        homeView = new HomeView(findViewById(R.id.swipe_ref_layout), this);
        init();
    }

    private void init() {
        setViewState(viewState);
        if (isCountryValid()) {
            if (viewState == HomeInteraction.View.STATE_LOADING) {
                homeView.showData(country);
                fetchNewData();
            } else
                onDataFetched(country);
        } else {
            if (viewState == HomeInteraction.View.STATE_ERROR)
                homeView.showError(true);
            else
                fetchNewData();

        }
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
        homeModel = new HomeModel();
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

    private void fetchNewData() {
        homeView.showProgress(true);
        homeView.showError(false);
        fetchData();
    }

    private void fetchData() {

        homeModel.fetchCountryInfo(new HomeInteraction.CountryInfoFetchListener() {
            @Override
            public void onSuccess(Country country) {
                Timber.d("Country fetched = %s", country.name);
                boolean isValid = country != null && country.isValid();
                if (isValid) {
                    onDataFetched(country);
                    if (homeView.getState() == HomeInteraction.View.STATE_LOADING) {
                        homeView.scrollToTop();
                    }
                    homeView.setState(HomeInteraction.View.STATE_LOADED);
                } else {
                    onError(new Error(Error.Type.Fetch));
                }


            }

            @Override
            public void onError(Error error) {
                Timber.e("Error fetching country info");
                if (isCountryValid()) {
                    homeView.showProgress(false);
                    homeView.setState(HomeInteraction.View.STATE_LOADED);
                    homeView.showInfo(getString(R.string.error_fetch));
                } else {
                    homeView.showInfo(getErrorMessage(error));
                    homeView.showProgress(false);
                    homeView.showError(true);
                    homeView.setState(HomeInteraction.View.STATE_ERROR);
                }
            }
        });
    }

    private void onDataFetched(Country country) {
        this.country = country;
        homeView.showProgress(false);
        homeView.showData(country);
    }

    @Override
    public void onRetry() {
        homeView.setState(HomeInteraction.View.STATE_LOADING);
        fetchNewData();
    }

    @Override
    public void onRefresh() {
        homeView.setState(HomeInteraction.View.STATE_LOADING);
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
