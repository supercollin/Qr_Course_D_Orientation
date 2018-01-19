package com.android.dev.qrcoursedorientation.presentation.fragment;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.android.dev.qrcoursedorientation.presentation.component.DisplayToast;
import com.android.dev.qrcoursedorientation.presentation.viewsinterfaces.QrView;

import com.android.dev.qrcoursedorientation.services.GPSTracker;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QrFragment extends Fragment implements QrView, ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    View view;
    List coordonnees = new ArrayList();
    private StartChronoInterface listener;

    public interface StartChronoInterface {
        // This can be any number of events to be sent to the activity
        void startChrono(String link);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (StartChronoInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(this.getContext());   // Programmatically initialize the scanner view
        view = mScannerView;                // Set the scanner view as the content view
        mScannerView.startCamera();
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
    public void handleResult(Result rawResult) {
        // Do something with the result here
        mScannerView.resumeCameraPreview(this);

        CheckPointManager.createCheckPoint(this.getContext(),rawResult.getText());
        Log.d("add", CheckPointManager.getCheckpointList().toString());

        if(CheckPointManager.getCheckpointList().size() == 1 && CheckPointManager.isRun()) {
            listener.startChrono("start chrono");
        }

        final Intent intent = new Intent("UPDATE_DATA");
        intent.putExtra("CHECKPOINT_LIST", "update");
        LocalBroadcastManager.getInstance(this.getContext()).sendBroadcast(intent);


    }
}