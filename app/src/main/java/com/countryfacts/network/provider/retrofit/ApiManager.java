package com.countryfacts.network.provider.retrofit;

/**
 * Our Network service provider
 */

public class ApiManager {
    private static ApiService apiService;

    private ApiManager() {

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
