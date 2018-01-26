package com.android.dev.qrcoursedorientation.presentation.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.android.dev.qrcoursedorientation.models.Checkpoint;
import com.android.dev.qrcoursedorientation.presentation.adapter.CourseCardAdapter;
import com.android.dev.qrcoursedorientation.presentation.adapter.QrCheckpointListAdapter;
import com.android.dev.qrcoursedorientation.presentation.presenters.QrCheckpointListPresenter;
import com.android.dev.qrcoursedorientation.presentation.viewmodel.QrCheckpointViewModel;
import com.android.dev.qrcoursedorientation.presentation.viewsinterfaces.QrCheckpointListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iem on 22/01/2018.
 */

public class CourseCardFragment extends Fragment implements QrCheckpointListView {
    private CardView mCardView;
    private View view;
    private QrCheckpointListPresenter qrCheckpointListPresenter;
    private QrCheckpointListAdapter qrCheckpointListAdapter;

    private List<QrCheckpointViewModel> checkpointViewModels;
    @BindView(R.id.recyclerviewchronicle) RecyclerView recyclerViewChronicle;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        view = inflater.inflate(R.layout.view_holder_course_chronicle, container, false);
        ButterKnife.bind(this,view);
        mCardView = view.findViewById(R.id.cardView);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CourseCardAdapter.MAX_ELEVATION_FACTOR);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        updateList(checkpointViewModels);
    }

    public void initRecyclerView() {
        qrCheckpointListAdapter = new QrCheckpointListAdapter();
        recyclerViewChronicle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewChronicle.setAdapter(qrCheckpointListAdapter);
    }

        public CardView getCardView() {
            return mCardView;
        }

    @Override
    public void updateList(List<QrCheckpointViewModel> CheckpointListViewModels) {
        qrCheckpointListAdapter.setQrCheckpointList(CheckpointListViewModels);
    }

    public void setCheckpointViewModels(List<QrCheckpointViewModel> checkpointViewModels) {
        this.checkpointViewModels = checkpointViewModels;
    }
}
