package com.android.dev.qrcoursedorientation.presentation.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.dev.qrcoursedorientation.R;

import com.android.dev.qrcoursedorientation.presentation.navigator.QrNavigator;
import com.android.dev.qrcoursedorientation.services.QrChronometer;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BaseActivity extends AppCompatActivity {

    QrNavigator qrNavigator;
    QrChronometer qrChronometer;
    boolean mServiceBound = false;
    TextView headerMessage;
    //@BindView(R.id.textViewMessage) TextView headerMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        headerMessage = (TextView) findViewById(R.id.textViewMessage);
        //ButterKnife.bind(this,this);

        Intent intent = new Intent(this, QrChronometer.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

        final Thread t = new Thread() {

            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1000);  //1000ms = 1 sec
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(qrChronometer != null){
                                    headerMessage.setText(qrChronometer.getTimestamp());
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();

        qrNavigator = new QrNavigator(getFragmentManager(), this);

        displayQRView();


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceBound) {
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }

    public void displayQRView(){
        qrNavigator.launchQrFragment();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            QrChronometer.MyBinder myBinder = (QrChronometer.MyBinder) service;
            qrChronometer = myBinder.getService();
            mServiceBound = true;
        }
    };
}
