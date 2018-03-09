package com.countryfacts.screen.home;

import android.app.Activity;
import android.os.Bundle;

import com.countryfacts.R;
import com.countryfacts.network.provider.retrofit.ApiManager;

import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


}
