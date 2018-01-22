package com.android.dev.qrcoursedorientation.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by Guillaume Colletaz on 22/01/2018.
 */

public class MailSend {

    public static void sendResultMail(Context context, String mail, int dossardNum){
        String filePath = Environment.getExternalStorageDirectory() + "/Course/2018-01-22-10-50.csv";
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Resultat Course dossard numero" + dossardNum);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse( "file://"+filePath));
        intent.setData(Uri.parse("mailto:" + mail)); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        context.startActivity(intent);
    }

    public static void sendQrMail(Context context, String mail, String foldername){
        String filePath = Environment.getExternalStorageDirectory() + "/QrCode/" + foldername + ".zip";
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Qrcode de la course d'orientation " + foldername);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse( "file://"+filePath));
        intent.setData(Uri.parse("mailto:" + mail)); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        context.startActivity(intent);
    }

}
