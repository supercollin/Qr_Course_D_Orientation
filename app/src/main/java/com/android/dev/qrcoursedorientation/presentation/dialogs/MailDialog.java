package com.android.dev.qrcoursedorientation.presentation.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.android.dev.qrcoursedorientation.utils.MailSend;

/**
 * Created by Guillaume Colletaz on 22/01/2018.
 */

public class MailDialog {
    public void showDilaog(final Context context){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setMessage("Un mail avec les résultat de la course va être envoyer à l'utilisateur");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MailSend.sendMail(context,"guillaume.colletaz01@gmail.com","",12);
            }
        }) ;
        alertDialog.show();
    }
}
