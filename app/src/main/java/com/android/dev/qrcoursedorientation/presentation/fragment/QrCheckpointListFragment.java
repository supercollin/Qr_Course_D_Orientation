package com.android.dev.qrcoursedorientation.presentation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.presentation.adapter.QrCheckpointListAdapter;
import com.android.dev.qrcoursedorientation.presentation.presenters.QrCheckpointListPresenter;
import com.android.dev.qrcoursedorientation.presentation.viewmodel.QrCheckpointListViewModel;
import com.android.dev.qrcoursedorientation.presentation.viewsinterfaces.QrCheckpointListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iem on 16/01/2018.
 */

public class QrCheckpointListFragment extends Fragment implements QrCheckpointListView{

    private View view;
    private QrCheckpointListPresenter qrCheckpointListPresenter;
    private QrCheckpointListAdapter qrCheckpointListAdapter;

    @BindView(R.id.recycler_view_qr_checkpoint_list) RecyclerView qrChekpointListRecyclerView;

    public static QrCheckpointListFragment newInstance(){
        QrCheckpointListFragment qrCheckpointListFragment = new QrCheckpointListFragment();
        return qrCheckpointListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_qr_checkpoint_list, container, false);
        ButterKnife.bind(this,view);
        initRecyclerView();

        return view;
    }

    @Override
    public void updateList(List<QrCheckpointListViewModel> qrCheckpointListViewModels){
        qrCheckpointListAdapter.setQrCheckpointList(qrCheckpointListViewModels);
    }

    private void initRecyclerView() {
        qrCheckpointListAdapter = new QrCheckpointListAdapter();
        qrChekpointListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        qrChekpointListRecyclerView.setAdapter(qrCheckpointListAdapter);
    }
}
