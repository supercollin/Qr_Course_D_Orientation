package com.android.dev.qrcoursedorientation.presentation.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.utils.MailSend;

/**
 * Created by Guillaume Colletaz on 22/01/2018.
 */

public class MailQrDialog {

    public void showDilaog(final Context context, final String mail, final String folderName){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setMessage(R.string.maildialog_qr_text);
        alertDialog.setPositiveButton(R.string.validate_alertdialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MailSend.sendQrMail(context,mail,folderName);
            }
        }) ;
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

}
