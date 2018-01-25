package com.android.dev.qrcoursedorientation.presentation.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.android.dev.qrcoursedorientation.managers.CourseManager;
import com.android.dev.qrcoursedorientation.presentation.activity.BaseActivity;

/**
 * Created by Guillaume Colletaz on 24/01/2018.
 */

public class AlreadyCourseIsRunDialog {
    public void showDialog(final Context context){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setMessage(R.string.already_course_running);

        alertDialog.setPositiveButton(R.string.validate_alertdialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CheckPointManager.cleanCheckpointManager();
                CourseManager.getCourseList().remove(CourseManager.getCurrentCourse());
                Intent intent = new Intent(context,BaseActivity.class);
                context.startActivity(intent);
            }
        }) ;
        alertDialog.setNegativeButton(R.string.cancel_alertdialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
