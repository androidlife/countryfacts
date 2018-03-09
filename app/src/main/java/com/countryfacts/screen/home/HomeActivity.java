package com.countryfacts.screen.home;

import com.countryfacts.R;
import com.countryfacts.base.BaseActivity;
import com.countryfacts.model.Country;
import com.countryfacts.model.Error;

import timber.log.Timber;

public class HomeActivity extends BaseActivity implements HomeInteraction.Controller {


    HomeView homeView;
    HomeModel homeModel;
    Country country;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate() {
        homeView = new HomeView(findViewById(R.id.swipe_ref_layout), this);
        homeModel = new HomeModel();
        fetchData();
    }

    private void fetchData() {
        homeView.showProgress(true);
        homeModel.fetchCountryInfo(new HomeInteraction.CountryInfoFetchListener() {
            @Override
            public void onSuccess(Country country) {
                Timber.d("Country fetched = %s", country.name);
                homeView.showData(country);
            }

            @Override
            public void onError(Error error) {
                Timber.e("Error fetching country info");
            }
        });
    }

    @Override
    public void onReload() {

    }
}
