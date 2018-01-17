package com.android.dev.qrcoursedorientation.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import com.android.dev.qrcoursedorientation.presentation.viewsinterfaces.QrView;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QrFragment extends Fragment implements QrView, ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    View view;
    private StartChronoInterface listener;

    public interface StartChronoInterface {
        // This can be any number of events to be sent to the activity
        void startChrono(String link);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StartChronoInterface) {
        } else {
            listener = (StartChronoInterface) context;
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
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
        Log.v("ok", rawResult.getText()); // Prints scan results
        mScannerView.resumeCameraPreview(this);
        listener.startChrono("start chrono");
    }
}