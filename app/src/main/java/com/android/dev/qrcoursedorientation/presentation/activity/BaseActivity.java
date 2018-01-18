package com.android.dev.qrcoursedorientation.presentation.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.android.dev.qrcoursedorientation.presentation.fragment.QrCheckpointListFragment;
import com.android.dev.qrcoursedorientation.presentation.fragment.QrFragment;
import com.android.dev.qrcoursedorientation.presentation.adapter.PagerAdapter;
import com.android.dev.qrcoursedorientation.presentation.transformers.ZoomOutPageTransformer;
import com.android.dev.qrcoursedorientation.services.QrChronometer;

import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BaseActivity extends FragmentActivity implements QrFragment.StartChronoInterface {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    QrChronometer qrChronometer;
    private boolean mServiceBound =false;

    @BindView(R.id.textViewMessage) TextView headerMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this,this);

        // Création de la liste de Fragments que fera défiler le PagerAdapter
        List fragments = new Vector();

        // Ajout des Fragments dans la liste
        fragments.add(Fragment.instantiate(this,QrFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,QrCheckpointListFragment.class.getName()));

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
                                    CheckPointManager.setTimeStamp(qrChronometer.getTimestamp());
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
    }

    @Override
    public String getChrono() {
        return (String) headerMessage.getText();
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
