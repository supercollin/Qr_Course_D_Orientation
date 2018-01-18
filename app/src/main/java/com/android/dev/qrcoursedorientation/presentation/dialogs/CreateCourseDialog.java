package com.android.dev.qrcoursedorientation.presentation.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.google.zxing.WriterException;

/**
 * Created by Guillaume Colletaz on 18/01/2018.
 */

public class CreateCourseDialog{

    private Context context;

    public CreateCourseDialog(Context context) {
        this.context = context;
    }

    public void show()
    {
        Log.d("show", "show: ");
        final Dialog d = new Dialog(context);
        d.setContentView(R.layout.create_course);
        Button validate = d.findViewById(R.id.validate);
        final Button cancel = d.findViewById(R.id.cancel);
        validate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
                try {
                    CheckPointManager.generateQr(2,context);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    d.show();

    }

}
