package com.android.dev.qrcoursedorientation.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.managers.CourseManager;

/**
 * Created by Guillaume Colletaz on 22/01/2018.
 */

public class MailSend {

    public static void sendResultMail(Context context, String mail, String dossardNum, String foldername){
        String filePath = Environment.getExternalStorageDirectory() + "/Course/"+ foldername + "_num_" + CourseManager.idRunner + ".csv";
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.object_mail_result) + dossardNum);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse( "file://"+filePath));
        intent.setData(Uri.parse("mailto:" + mail)); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        context.startActivity(intent);
    }

    public static void sendQrMail(Context context, String mail, String foldername){
        String filePath = Environment.getExternalStorageDirectory() + "/QrCode/" + foldername + ".zip";
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.object_mail_qr) + foldername);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse( "file://"+filePath));
        intent.setData(Uri.parse("mailto:" + mail)); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        context.startActivity(intent);
    }

}
