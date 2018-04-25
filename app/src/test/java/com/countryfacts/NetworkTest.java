package com.countryfacts;

import com.countryfacts.model.Country;
import com.countryfacts.network.provider.retrofit.ApiManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NetworkTest {

    @Rule
    public Timeout timeout = Timeout.seconds(3);

    @Test
    public void fetchJsonData() throws Exception {
        Country country = ApiManager.getApiService().getCountryInfo().blockingFirst();
        assertTrue(country.name != null);
        assertTrue(country.countryInfos !=null && country.countryInfos.size()>0);
        assertTrue(country.countryInfos.size()==14);
    }
}