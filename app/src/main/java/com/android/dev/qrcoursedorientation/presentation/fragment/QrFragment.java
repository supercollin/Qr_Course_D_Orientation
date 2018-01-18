
<<<<<<< HEAD
        // Do something with the result here
        Log.v("ok", rawResult.getText()); // Prints scan results
        mScannerView.resumeCameraPreview(this);
        if(CheckPointManager.getCheckpointList().size() == 0 && CheckPointManager.isRun()) {
            listener.startChrono("start chrono");
        }
        CheckPointManager.createCheckPoint(rawResult.getText(), 0, 0);
        Log.d("checkpoint", CheckPointManager.getCheckpointList().toString());
    }
}
        // Do something with the result here
        Log.v("ok", rawResult.getText()); // Prints scan results
        mScannerView.resumeCameraPreview(this);
        if(CheckPointManager.getCheckpointList().size() == 0 && CheckPointManager.isRun()) {
            listener.startChrono("start chrono");
        }
        CheckPointManager.createCheckPoint(rawResult.getText(), 0, 0);
        Log.d("checkpoint", CheckPointManager.getCheckpointList().toString());
        //Log.d("checkpoint", CheckPointManager.getCheckpointList().toString());
=======
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.android.dev.qrcoursedorientation.presentation.activity.BaseActivity;
import com.android.dev.qrcoursedorientation.presentation.viewsinterfaces.QrView;
>>>>>>> PutDataIntoRecyclerView

        //On envoie les données au fragment QR_CheckpointList
        final Intent intent = new Intent("UPDATE_DATA");
        intent.putExtra("CHECKPOINT_LIST", "update");
        LocalBroadcastManager.getInstance(this.getContext()).sendBroadcast(intent);











<<<<<<< HEAD
=======
    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("ok", rawResult.getText()); // Prints scan results
        mScannerView.resumeCameraPreview(this);
        listener.startChrono("start chrono");
        CheckPointManager.createCheckPoint(rawResult.getText(), 0, 0);
        //Log.d("checkpoint", CheckPointManager.getCheckpointList().toString());

        //On envoie les données au fragment QR_CheckpointList
        final Intent intent = new Intent("UPDATE_DATA");
        intent.putExtra("CHECKPOINT_LIST", "update");
        LocalBroadcastManager.getInstance(this.getContext()).sendBroadcast(intent);











>>>>>>> PutDataIntoRecyclerView
    }
}