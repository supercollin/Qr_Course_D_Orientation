package com.android.dev.qrcoursedorientation.presentation.viewsinterfaces;

import com.android.dev.qrcoursedorientation.presentation.viewmodel.QrCheckpointViewModel;

import java.util.List;

/**
 * Created by iem on 16/01/2018.
 */

public interface QrCheckpointListView {
    void updateList(List<QrCheckpointViewModel> qrCheckpointListViewModels);

}
