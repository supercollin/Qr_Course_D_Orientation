package com.android.dev.qrcoursedorientation.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.presentation.viewholder.QrCheckpointListViewHolder;
import com.android.dev.qrcoursedorientation.presentation.viewmodel.QrCheckpointViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iem on 16/01/2018.
 */

public class QrCheckpointListAdapter extends RecyclerView.Adapter<QrCheckpointListViewHolder> {


    private List<QrCheckpointViewModel> checkpointList;

    public QrCheckpointListAdapter() {
        this.checkpointList = new ArrayList<>();
    }


    public void setQrCheckpointList(List<QrCheckpointViewModel> checkpointList) {
        this.checkpointList = checkpointList;
        notifyDataSetChanged();
    }

    @Override
    public QrCheckpointListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.viewholder, parent, false);

        return new QrCheckpointListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QrCheckpointListViewHolder holder, int position) {
        holder.bind(checkpointList.get(position));
    }

    @Override
    public int getItemCount() {
        return checkpointList.size();
    }
}
