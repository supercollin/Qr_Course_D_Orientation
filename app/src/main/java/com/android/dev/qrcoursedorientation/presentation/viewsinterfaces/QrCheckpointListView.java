package com.android.dev.qrcoursedorientation.presentation.viewsinterfaces;

import com.android.dev.qrcoursedorientation.presentation.viewmodel.QrCheckpointListViewModel;

import java.util.List;

/**
 * Created by iem on 16/01/2018.
 */

public interface QrCheckpointListView {
    public void updateList(List<QrCheckpointListViewModel> qrCheckpointListViewModels);

}
