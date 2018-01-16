package com.android.dev.qrcoursedorientation.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.navigator.QrNavigator;


public class BaseActivity extends AppCompatActivity {

    QrNavigator qrNavigator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        qrNavigator = new QrNavigator(getFragmentManager(),this);
        qrNavigator.launchQrFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }



}
