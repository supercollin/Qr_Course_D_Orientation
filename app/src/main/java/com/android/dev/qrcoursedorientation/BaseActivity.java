package com.android.dev.qrcoursedorientation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.android.dev.qrcoursedorientation.TimerService.MyBinder;
import com.google.zxing.WriterException;

public class BaseActivity extends AppCompatActivity {
    TimerService timerService;
    boolean mServiceBound = false;
    TextView timestampText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        timestampText = (TextView) findViewById(R.id.timestamp_text);
        Button printTimestampButton = (Button) findViewById(R.id.print_timestamp);
        Button stopServiceButon = (Button) findViewById(R.id.stop_service);


        final Thread t=new Thread(){

            @Override
            public void run(){
                while(!isInterrupted()){
                    try {
                        Thread.sleep(1000);  //1000ms = 1 sec
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timestampText.setText(timerService.getTimestamp());
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();

        stopServiceButon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mServiceBound) {
                    unbindService(mServiceConnection);
                    mServiceBound = false;
                }
                Intent intent = new Intent(BaseActivity.this,
                        TimerService.class);
                stopService(intent);
            }
        });

        printTimestampButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", "onClick: ");
                try {

                    Log.d("try", "onClick: ");
                    Bitmap QR;
                    QR = QrConverter.TextToImageEncode("bite@bite.com!#Depart",500);
                    QrConverter.saveImage(BaseActivity.this,QR);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TimerService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceBound) {
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timerService != null) {
            timestampText.setText(timerService.getTimestamp());
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MyBinder myBinder = (MyBinder) service;
            timerService = myBinder.getService();
            mServiceBound = true;
        }
    };
}