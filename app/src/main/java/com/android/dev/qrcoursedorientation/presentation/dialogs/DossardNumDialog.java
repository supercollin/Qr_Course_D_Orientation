package com.android.dev.qrcoursedorientation.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.android.dev.qrcoursedorientation.managers.CourseManager;
import com.android.dev.qrcoursedorientation.presentation.activity.BaseActivity;
import com.android.dev.qrcoursedorientation.presentation.activity.StartActivity;
import com.google.zxing.WriterException;

import java.util.Objects;

/**
 * Created by Guillaume Colletaz on 25/01/2018.
 */

public class DossardNumDialog {

    public void showDialog(final Context context)
    {

        final Dialog d = new Dialog(context);
        d.setContentView(R.layout.run_dialog);

        Button validate = d.findViewById(R.id.RunDialogValidate);
        Button cancel = d.findViewById(R.id.RunDialogCancel);
        final EditText numDossard = d.findViewById(R.id.RunDialogDossard);

        validate.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                if(numDossard.getText().length()!=0) {
                    CheckPointManager.setTimeStampBase(0);
                    String dossard = numDossard.getText().toString();
                    CourseManager.setIdRunner(dossard);
                    d.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,StartActivity.class);
                context.startActivity(intent);
                d.dismiss();
            }
        });

        d.setCancelable(false);
        d.show();

    }

}
