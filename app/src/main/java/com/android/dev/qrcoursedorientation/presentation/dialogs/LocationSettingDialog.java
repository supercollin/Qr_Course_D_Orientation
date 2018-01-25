package com.android.dev.qrcoursedorientation.presentation.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import com.android.dev.qrcoursedorientation.R;

/**
 * Created by Guillaume Colletaz on 19/01/2018.
 */

public class LocationSettingDialog {

    public void showDialog(final Context context){
        //locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setMessage(R.string.gps_not_activated_dialog);
        alertDialog.setPositiveButton(R.string.go_to_setting, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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
