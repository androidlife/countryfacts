package com.countryfacts.network.provider.retrofit;

import com.countryfacts.model.Country;
import com.countryfacts.network.Urls;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 */

public interface ApiService {
    @GET(Urls.FACTS)
    Observable<Country> getCountryInfo();
}
