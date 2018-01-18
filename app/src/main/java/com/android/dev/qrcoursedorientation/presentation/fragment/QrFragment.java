
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

        //On envoie les données au fragment QR_CheckpointList
        final Intent intent = new Intent("UPDATE_DATA");
        intent.putExtra("CHECKPOINT_LIST", "update");
        LocalBroadcastManager.getInstance(this.getContext()).sendBroadcast(intent);











    }
}