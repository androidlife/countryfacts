package com.countryfacts.network.provider.retrofit;

import com.countryfacts.BuildConfig;
import com.countryfacts.network.Urls;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */

public class ApiManager {
    static volatile Retrofit retrofit;
    private static ApiService apiService;

    private ApiManager() {

    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (ApiManager.class) {
                if (retrofit == null) {
                    OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
                    okhttpClientBuilder.connectTimeout(1, TimeUnit.MINUTES);
                    okhttpClientBuilder.readTimeout(1, TimeUnit.MINUTES);
                    if (BuildConfig.DEBUG)
                        okhttpClientBuilder.addInterceptor(getLoggingInterceptor(HttpLoggingInterceptor.Level.BASIC));
                    //TODO add a custom converter to ignore null values
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Urls.BASE)
                            .client(okhttpClientBuilder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }

        }
        return retrofit;
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
                    apiService = getRetrofit().create(ApiService.class);
            }
        }
        return apiService;
    }

}
