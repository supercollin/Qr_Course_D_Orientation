package com.android.dev.qrcoursedorientation.presentation.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.android.dev.qrcoursedorientation.managers.CourseManager;
import com.android.dev.qrcoursedorientation.presentation.activity.BaseActivity;

/**
 * Created by Guillaume Colletaz on 24/01/2018.
 */

public class AlreadyCourseIsRunDialog {
    public void showDialog(final Context context){
        //locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setMessage("Une course est déjà en cours cette action la suprimera");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CheckPointManager.cleanCheckpointManager();
                CourseManager.getCourseList().remove(CourseManager.getCurrentCourse());
                Intent intent = new Intent(context,BaseActivity.class);
                context.startActivity(intent);
            }
        }) ;
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
