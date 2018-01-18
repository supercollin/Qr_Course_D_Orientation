package com.android.dev.qrcoursedorientation.presentation.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.android.dev.qrcoursedorientation.models.Checkpoint;
import com.android.dev.qrcoursedorientation.presentation.adapter.QrCheckpointListAdapter;
import com.android.dev.qrcoursedorientation.presentation.presenters.QrCheckpointListPresenter;
import com.android.dev.qrcoursedorientation.presentation.viewmodel.QrCheckpointViewModel;
import com.android.dev.qrcoursedorientation.presentation.viewsinterfaces.QrCheckpointListView;

import java.util.ArrayList;
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

    @BindView(R.id.recycler_view_qr_checkpoint_list) RecyclerView qrCheckpointListRecyclerView;

    public static QrCheckpointListFragment newInstance(){
        QrCheckpointListFragment qrCheckpointListFragment = new QrCheckpointListFragment();

        return qrCheckpointListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_qr_checkpoint_list, container, false);
        ButterKnife.bind(this,view);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter("UPDATE_DATA"));


        return view;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();



//        List<QrCheckpointViewModel> qrCheckpointListViewModels = new ArrayList<>();
//        qrCheckpointListViewModels.add(new QrCheckpointViewModel(new Checkpoint("1", "789", 10.0, 5.5)));
//        qrCheckpointListViewModels.add(new QrCheckpointViewModel(new Checkpoint("2", "2221", 10.0, 5.5)));
//        qrCheckpointListViewModels.add(new QrCheckpointViewModel(new Checkpoint("3", "456", 10.0, 5.5)));
//        qrCheckpointListViewModels.add(new QrCheckpointViewModel(new Checkpoint("4", "958", 10.0, 5.5)));
//        updateList(qrCheckpointListViewModels);
    }

    @Override
    public void updateList(List<QrCheckpointViewModel> qrCheckpointListViewModels){
        qrCheckpointListAdapter.setQrCheckpointList(qrCheckpointListViewModels);
    }

    private void initRecyclerView() {
        qrCheckpointListAdapter = new QrCheckpointListAdapter();
        qrCheckpointListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        qrCheckpointListRecyclerView.setAdapter(qrCheckpointListAdapter);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("UPDATE_DATA".equals(intent.getAction()) == true) {
                List<Checkpoint> checkpointList = CheckPointManager.getCheckpointList();
                List<QrCheckpointViewModel> qrCheckpointViewModels = new ArrayList<>();
                for(int i=0; i < checkpointList.size(); i++){
                    qrCheckpointViewModels.add(new QrCheckpointViewModel(checkpointList.get(i)));
                }

                updateList(qrCheckpointViewModels);
            }
        }
    };

}
