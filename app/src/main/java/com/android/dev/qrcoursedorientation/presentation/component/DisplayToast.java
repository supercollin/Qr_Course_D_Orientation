package com.android.dev.qrcoursedorientation.presentation.component;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Guillaume Colletaz on 19/01/2018.
 */

public class DisplayToast {

    public  static void displayToast( Context context, String message){
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}
