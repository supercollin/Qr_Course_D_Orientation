package com.android.dev.qrcoursedorientation.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.android.dev.qrcoursedorientation.managers.CourseManager;
import com.android.dev.qrcoursedorientation.utils.MailSend;

/**
 * Created by Guillaume Colletaz on 22/01/2018.
 */

public class MailDialog {
    public void showDialog(final Context context){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("Un mail avec les résultat de la course va être envoyer à l'utilisateur");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MailSend.sendResultMail(context,CourseManager.getCourseList().get(CourseManager.getCourseList().size()-1).getMailOrganizer(),CourseManager.idRunner, CourseManager.getCurrentCourse().getDate());
            }
        }) ;
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
