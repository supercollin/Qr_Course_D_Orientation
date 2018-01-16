package com.android.dev.qrcoursedorientation.presentation.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.presentation.viewmodel.QrCheckpointListViewModel;

import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iem on 16/01/2018.
 */

public class QrCheckpointListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_Qr_Checkpoint_List_textView_Id_Balise) TextView idBalise;
    @BindView(R.id.fragment_Qr_Checkpoint_List_textView_Time) TextView time;

    public QrCheckpointListViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    public void bind( final QrCheckpointListViewModel checkpoint){
        idBalise.setText(checkpoint.getIdBalise());
        time.setText(checkpoint.getTime());
    }
}
