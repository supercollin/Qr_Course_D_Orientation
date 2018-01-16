package com.android.dev.qrcoursedorientation.presentation.viewmodel;

import com.android.dev.qrcoursedorientation.models.Checkpoint;

/**
 * Created by iem on 16/01/2018.
 */

public class QrCheckpointListViewModel {

    private Checkpoint checkpoint;

    public QrCheckpointListViewModel(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public String getIdBalise() {
        return checkpoint.getIdBalise();
    }

    public String getTime() {
        return checkpoint.getTime();
    }


}
