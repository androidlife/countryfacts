package com.countryfacts.network.provider.retrofit;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 */

public class Interceptors {

    public static HttpLoggingInterceptor getBasicLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return loggingInterceptor;
    }

}
