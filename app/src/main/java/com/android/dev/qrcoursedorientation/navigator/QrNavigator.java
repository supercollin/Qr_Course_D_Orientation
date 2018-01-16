package com.android.dev.qrcoursedorientation.navigator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.fragment.QrFragment;
import com.android.dev.qrcoursedorientation.views.viewsinterfaces.QrView;

/**
 * Created by iem on 16/01/2018.
 */

public class QrNavigator {

    private final FragmentManager fragmentManager;
    private final Context context;

    private final static int QR_FRAGMENT = 0;
    private int currentFragment;

    public QrNavigator(FragmentManager fragmentManager, Context context) {
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    public void launchQrFragment() {
        QrView qrView = QrFragment.newInstance();


        fragmentManager
                .beginTransaction()
                .addToBackStack(context.getString(R.string.qr_view))
                .replace(R.id.root_layout, (Fragment) qrView)
                .commit();

        currentFragment = QR_FRAGMENT;
    }
}
