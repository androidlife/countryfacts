package com.countryfacts.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.countryfacts.MainApplication;

/**
 */

public class GeneralUtil {
    public static boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MainApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
