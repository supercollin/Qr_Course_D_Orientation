package com.android.dev.qrcoursedorientation.presentation.fragment;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Toast;

import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.android.dev.qrcoursedorientation.managers.CourseManager;
import com.android.dev.qrcoursedorientation.models.Checkpoint;
import com.android.dev.qrcoursedorientation.presentation.component.DisplayToast;
import com.android.dev.qrcoursedorientation.presentation.viewmodel.QrCheckpointViewModel;
import com.android.dev.qrcoursedorientation.presentation.viewsinterfaces.QrView;

import com.android.dev.qrcoursedorientation.services.GPSTracker;
import com.android.dev.qrcoursedorientation.utils.FileWriter;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QrFragment extends Fragment implements QrView, ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    View view;
    List coordonnees = new ArrayList();
    Vibrator vibrator;
    private StartChronoInterface listener;

    public interface StartChronoInterface {
        // This can be any number of events to be sent to the activity
        void startChrono(String link);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (StartChronoInterface) context;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(this.getContext());   // Programmatically initialize the scanner view
        view = mScannerView;                // Set the scanner view as the content view
        mScannerView.startCamera();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter("SET_FLASH"));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        mScannerView.resumeCameraPreview(this);

        if(CheckPointManager.createCheckPoint(this.getContext(),rawResult.getText())){
            if(CheckPointManager.getCheckpointList().size() == 1) {
                listener.startChrono("start chrono");
            }
            CourseManager.createCourse(this.getContext(), rawResult.getText(),CheckPointManager.getTimeStampBase());
            try {
                FileWriter.fileWriter(CourseManager.getCourseList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        vibrator.vibrate(100);

        final Intent intent = new Intent("UPDATE_DATA");
        intent.putExtra("CHECKPOINT_LIST", "update");
        LocalBroadcastManager.getInstance(this.getContext()).sendBroadcast(intent);
    }


    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("SET_FLASH".equals(intent.getAction()) == true) {
                if(mScannerView.getFlash()){
                    mScannerView.setFlash(false);
                }else{
                    mScannerView.setFlash(true);
                }
            }
        }
    };

}