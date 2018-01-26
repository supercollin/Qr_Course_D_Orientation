package com.android.dev.qrcoursedorientation.presentation.adapter;

import android.support.v7.widget.CardView;

/**
 * Created by iem on 22/01/2018.
 */

public interface CourseCardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();



}
