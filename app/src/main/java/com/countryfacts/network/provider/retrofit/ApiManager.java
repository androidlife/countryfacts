package com.countryfacts.network.provider.retrofit;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Our Network service provider
 */

public class ApiManager {
    private static ApiService apiService;

    private ApiManager() {

    }


    private static HttpLoggingInterceptor getLoggingInterceptor(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (ApiManager.class) {
                if (apiService == null)
                    apiService = RetrofitProvider.getRetrofit().create(ApiService.class);
            }
        }
        return apiService;
    }

}
