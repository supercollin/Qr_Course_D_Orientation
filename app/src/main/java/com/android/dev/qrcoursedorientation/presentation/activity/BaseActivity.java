package com.android.dev.qrcoursedorientation.presentation.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.android.dev.qrcoursedorientation.managers.CourseManager;
import com.android.dev.qrcoursedorientation.presentation.dialogs.DossardNumDialog;
import com.android.dev.qrcoursedorientation.presentation.dialogs.InternetSettingsDialog;
import com.android.dev.qrcoursedorientation.presentation.dialogs.LocationSettingDialog;
import com.android.dev.qrcoursedorientation.presentation.fragment.QrCheckpointListFragment;
import com.android.dev.qrcoursedorientation.presentation.fragment.QrFragment;
import com.android.dev.qrcoursedorientation.presentation.adapter.PagerAdapter;
import com.android.dev.qrcoursedorientation.presentation.transformers.ZoomOutPageTransformer;
import com.android.dev.qrcoursedorientation.services.QrChronometer;
import com.android.dev.qrcoursedorientation.utils.FileWriter;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class BaseActivity extends FragmentActivity implements QrFragment.StartChronoInterface {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    QrChronometer qrChronometer;
    private boolean mServiceBound = false;
    private LocationRequest locationRequest;
    private List<Double> coord = new ArrayList<>();

    private boolean flash = false;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    final Thread t = new Thread() {

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    if (CheckPointManager.isRun()) {
                        Thread.sleep(1000);  //1000ms = 1 sec
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (qrChronometer != null) {
                                    if(CheckPointManager.isFirstimeChrono()){
                                        Log.d( "run: ","a");
                                        CheckPointManager.setTimeStampBase(0);
                                        qrChronometer.setTimeStampBase(CheckPointManager.getTimeStampBase());
                                        headerMessage.setText(qrChronometer.getTimestamp());
                                        CheckPointManager.setFirstimeChrono(false);
                                    }else {
                                        headerMessage.setText(qrChronometer.getTimestamp());
                                        CheckPointManager.setTimeStamp(qrChronometer.getTimestamp());
                                        CheckPointManager.setTimeStampBase(qrChronometer.getTimeStampBase());
                                        CourseManager.getCurrentCourse().setTimestamp(CheckPointManager.getTimeStampBase());
                                    }
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @BindView(R.id.textViewMessage)
    TextView headerMessage;
    @BindView(R.id.flash_icon)
    ImageButton flashIcon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this, this);

        getCoord();
        if(CheckPointManager.isRun()){
            startChrono("start chrono");
        }else{
            CheckPointManager.setTimeStampBase(0);
            CheckPointManager.setFirstimeChrono(true);
            DossardNumDialog dossardNumDialog =new DossardNumDialog();
            dossardNumDialog.showDialog(this);
        }
        positionActivated();

        // Création de la liste de Fragments que fera défiler le PagerAdapter
        List<Fragment> fragments = new Vector<>();

        // Ajout des Fragments dans la liste
        fragments.add(Fragment.instantiate(this, QrFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, QrCheckpointListFragment.class.getName()));

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = findViewById(R.id.pager);
        // Affectation de l'adapter au ViewPager
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
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
    public void startChrono(String link) {
        Intent intent = new Intent(this, QrChronometer.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_DEBUG_UNBIND);
        t.start();
    }

    public void stopChrono(){
        t.interrupt();
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

    public void getCoord() {

        coord = new ArrayList<>();
        coord.add(0.0);
        coord.add(0.0);

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }else{
            getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            // do work here
                            onLocationChanged(locationResult.getLastLocation());
                        }
                    },
                    Looper.myLooper()
            );
        }
    }

    public void onLocationChanged(Location location) {
        // You can now create a LatLng Object for use with maps
        CheckPointManager.setLongitude(location.getLongitude());
        CheckPointManager.setLatitude(location.getLatitude());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            FileWriter.fileWriter(CourseManager.getCourseList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            FileWriter.fileWriter(CourseManager.getCourseList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(qrChronometer!=null) {
            qrChronometer.setTimeStampBase(0);
        }
        try {
            FileWriter.fileWriter(CourseManager.getCourseList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void positionActivated(){
        LocationManager locManager;
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationSettingDialog locationSettingDialog = new LocationSettingDialog();
            locationSettingDialog.showDialog(this);
        }
    }


    @OnClick(R.id.flash_icon)
    public void flashIconOnClick(){
        if(flash){
            flash = false;
            flashIcon.setImageResource(R.drawable.ic_flash_on);
        }else{
            flash = true;
            flashIcon.setImageResource(R.drawable.ic_flash_off);
        }

        final Intent intent = new Intent("SET_FLASH");
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
    }
}


