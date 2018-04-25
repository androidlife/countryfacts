package com.countryfacts.network.provider.retrofit;

import com.countryfacts.BuildConfig;
import com.countryfacts.model.Country;
import com.countryfacts.model.deserializer.CountryDeserializer;
import com.countryfacts.network.Urls;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple way to get Retrofit instance
 */

public class RetrofitProvider {
    static volatile Retrofit retrofit;

    private RetrofitProvider() {

    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitProvider.class) {
                if (retrofit == null) {
                    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
                    okHttpClientBuilder.connectTimeout(1, TimeUnit.MINUTES);
                    okHttpClientBuilder.readTimeout(1, TimeUnit.MINUTES);
                    if (BuildConfig.DEBUG)
                        okHttpClientBuilder.addInterceptor(Interceptors.getBasicLoggingInterceptor());
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Country.class, new CountryDeserializer());
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Urls.BASE)
                            .client(okHttpClientBuilder.build())
                            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }

        }
        return retrofit;
    }
}
