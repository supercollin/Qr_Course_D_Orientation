package com.android.dev.qrcoursedorientation.presentation.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.presentation.adapter.CourseCardAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iem on 22/01/2018.
 */

public class CourseCardFragment extends Fragment{
    private CardView mCardView;
    private View view;

    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.view_holder_course_chronicle, container, false);
            mCardView = view.findViewById(R.id.cardView);
            mCardView.setMaxCardElevation(mCardView.getCardElevation()
                    * CourseCardAdapter.MAX_ELEVATION_FACTOR);
            return view;
        }

        public CardView getCardView() {
            return mCardView;
        }

}
