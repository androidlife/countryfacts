package com.countryfacts;

import com.countryfacts.model.Country;
import com.countryfacts.network.provider.retrofit.ApiManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

/**
 * Simple test to see whether network callback
 * and json parsing is done properly or not
 * This is just a brute force mechanism of testing the network
 * where we assume that there is always a fetch success
 */
public class NetworkTest {

    @Rule
    public Timeout timeout = Timeout.seconds(3);

    @Test
    public void fetchJsonData() throws Exception {
        Country country = ApiManager.getApiService().getCountryInfo().blockingFirst();
        assertTrue(country.name != null);
        assertTrue(country.countryInfos != null && country.countryInfos.size() > 0);
    }
}