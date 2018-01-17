package com.android.dev.qrcoursedorientation.presentation.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.dev.qrcoursedorientation.presentation.viewsinterfaces.QrView;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by iem on 16/01/2018.
 */

public class QrFragment extends Fragment implements QrView, ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    View view;
    private StartChronoInterface listener;

    public static QrFragment newInstance() {
        QrFragment fragment = new QrFragment();

        return fragment;
    }

    public interface StartChronoInterface {
        // This can be any number of events to be sent to the activity
        public void startChrono(String link);
    }

    @Override
    public void onAttach(Context context) {
        Log.d("a", "onAttach: ");
        super.onAttach(context);
        if (context instanceof StartChronoInterface) {
            listener = (StartChronoInterface) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("a", "onCreateView: ");
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
