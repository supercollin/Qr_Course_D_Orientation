package com.android.dev.qrcoursedorientation.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.android.dev.qrcoursedorientation.managers.CourseManager;

/**
 * Created by Guillaume Colletaz on 16/01/2018.
 */

public class QrChronometer extends Service {

    private IBinder mBinder = new MyBinder();
    private Chronometer QrChronometer;

    @Override
    public void onCreate() {
        super.onCreate();
        QrChronometer = new Chronometer(this);
        if(CheckPointManager.getTimeStampBase() == 0){
            CheckPointManager.setTimeStampBase(SystemClock.elapsedRealtime());
        }
        QrChronometer.setBase(CheckPointManager.getTimeStampBase());
        QrChronometer.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        QrChronometer.stop();
    }

    public String getTimestamp() { //return timeStamp between OnPause and OnResume
        long elapsedMillis = SystemClock.elapsedRealtime()
                - QrChronometer.getBase();
        int hours = (int) (elapsedMillis / 3600000);
        int minutes = (int) (elapsedMillis - hours * 3600000) / 60000;
        int seconds = (int) (elapsedMillis - hours * 3600000 - minutes * 60000) / 1000;
        return hours + ":" + minutes + ":" + seconds ;
    }

    public long getTimeStampBase(){
        return QrChronometer.getBase();
    }

    public void startChronometer() {
        QrChronometer.start();
    }

    public class MyBinder extends Binder {
        public QrChronometer getService() {
            return QrChronometer.this;
        }
    }

    public void setTimeStampBase(long base){
        if(base != 0){
            QrChronometer.setBase(base);
        }else{
            QrChronometer.setBase(SystemClock.elapsedRealtime());
        }
    }
}
