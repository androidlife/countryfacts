package com.countryfacts.screen.home;

import android.os.Bundle;

import com.countryfacts.R;
import com.countryfacts.base.BaseActivity;
import com.countryfacts.model.Country;
import com.countryfacts.model.Error;

import timber.log.Timber;

/**
 * Here MVC architecture is follower, where
 * Activity acts as a controller and mediator
 * between the View and the Model layouer
 */
public class HomeActivity extends BaseActivity implements HomeContracts.Controller {


    private HomeView homeView;
    private HomeModel homeModel;
    private Country country;

    //key for saving Country and View State
    private static final String SAVE_COUNTRY = "Country";
    private static final String SAVE_VIEW_STATE = "ViewState";
    private int viewState = HomeView.STATE_EMPTY;

    //callback after necessary permission is given
    @Override
    protected void onPermissionChecked() {
        setContentView(R.layout.activity_home);
        homeView = new HomeView(findViewById(R.id.swipe_ref_layout), this);
        init();
    }

    //initializing the view and requesting model layer is necessary
    private void init() {
        setViewState(viewState);
        if (isCountryValid()) {
            if (viewState == HomeContracts.View.STATE_LOADING) {
                homeView.populateList(country);
                fetchNewData();
            } else
                onDataFetched(country);
        } else {
            if (viewState == HomeContracts.View.STATE_ERROR)
                homeView.showError(true);
            else
                fetchNewData();

        }
    }

    //setting the view state
    private void setViewState(int state) {
        homeView.setState(state);
    }

    //pausing and resuming the model layer
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


    //saving the view state along with data
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isCountryValid())
            outState.putParcelable(SAVE_COUNTRY, country);
        outState.putInt(SAVE_VIEW_STATE, homeView.getState());

    }

    //resorting the view state along with data if present
    @Override
    protected void onCreated(Bundle savedInstanceState) {
        homeModel = new HomeModel();
        if (savedInstanceState == null)
            return;
        if (savedInstanceState.containsKey(SAVE_COUNTRY))
            country = savedInstanceState.getParcelable(SAVE_COUNTRY);
        if (savedInstanceState.containsKey(SAVE_VIEW_STATE))
            viewState = savedInstanceState.getInt(SAVE_VIEW_STATE);

    }

    private boolean isCountryValid() {
        return country != null && country.isValid();
    }

    //fetching the entire new data where there is slight change in view only
    private void fetchNewData() {
        homeView.showProgress(true);
        homeView.showError(false);
        fetchData();
    }

    //fetching the data from model layer
    private void fetchData() {
        homeModel.fetchCountryInfo(new HomeContracts.CountryInfoFetchListener() {
            @Override
            public void onSuccess(Country country) {
                Timber.d("Country fetched = %s", country.name);
                boolean isValid = country != null && country.isValid();
                if (isValid) {
                    onDataFetched(country);
                    if (homeView.getState() == HomeContracts.View.STATE_LOADING) {
                        homeView.scrollToTop();
                    }
                    homeView.setState(HomeContracts.View.STATE_LOADED);
                } else {
                    onError(new Error(Error.Type.Fetch));
                }


            }

            @Override
            public void onError(Error error) {
                Timber.e("Error fetching country info");
                if (isCountryValid()) {
                    homeView.showProgress(false);
                    homeView.setState(HomeContracts.View.STATE_LOADED);
                    homeView.showInfo(getString(R.string.error_fetch));
                } else {
                    homeView.showInfo(getErrorMessage(error));
                    homeView.showProgress(false);
                    homeView.showError(true);
                    homeView.setState(HomeContracts.View.STATE_ERROR);
                }
            }
        });
    }

    private void onDataFetched(Country country) {
        this.country = country;
        homeView.showProgress(false);
        homeView.populateList(country);
    }

    //retry in case of any error like network
    @Override
    public void onRetry() {
        homeView.setState(HomeContracts.View.STATE_LOADING);
        fetchNewData();
    }

    //refresh action
    @Override
    public void onRefresh() {
        homeView.setState(HomeContracts.View.STATE_LOADING);
        fetchData();
    }

    //simple method to get the error message as per error type
    private String getErrorMessage(Error error) {
        switch (error.type) {
            case Network:
                return getString(R.string.error_network);
            default:
                return getString(R.string.error_fetch);

        }
    }
}
