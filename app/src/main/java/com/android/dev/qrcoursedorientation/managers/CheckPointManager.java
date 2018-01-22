package com.android.dev.qrcoursedorientation.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.android.dev.qrcoursedorientation.models.Checkpoint;
import com.android.dev.qrcoursedorientation.presentation.activity.BaseActivity;
import com.android.dev.qrcoursedorientation.presentation.component.DisplayToast;
import com.android.dev.qrcoursedorientation.presentation.dialogs.MailDialog;
import com.android.dev.qrcoursedorientation.utils.MailSend;
import com.android.dev.qrcoursedorientation.utils.QrConverter;
import com.android.dev.qrcoursedorientation.utils.WriteCsv;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Guillaume Colletaz on 17/01/2018.
 */

public class CheckPointManager {


    private static List<Checkpoint> checkpointList = new ArrayList<>();
    private static String timeStamp = "0:0:0";
    private static double longitude;
    private static double latitude;

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        CheckPointManager.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        CheckPointManager.latitude = latitude;
    }

    private static boolean run = false;

    public static void setTimeStamp(String time){
        timeStamp = time;
    }

    public static boolean isRun() {
        return run;
    }

    public static void createCheckPoint(Context context, String qrResuult) {

        Checkpoint tmp;

        String res = "";
        String patternStr = "#([\\w\\W]*)#([\\w\\W]*)#([\\w\\W]*)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(qrResuult);
        boolean matchFound = matcher.find();
        if (matchFound && matcher.groupCount() >= 1) {
            Log.d("grp1", matcher.group(1));
            Log.d("grp2", matcher.group(2));
            Log.d("grp3", matcher.group(3));
            Log.d("grp3", latitude + "" +longitude);

            if (Objects.equals(matcher.group(2), "start")) {
                run = true;
                tmp = new Checkpoint(matcher.group(1), "0:0:0", latitude, longitude);
                if(!containInList(tmp)) {
                    checkpointList.add(tmp);
                    DisplayToast.displayToast(context,"Scan réussi\nla course à commencé");
                }else {
                    DisplayToast.displayToast(context, "Le QrCode à déjà été Scanné");
                }
            } else if (Objects.equals(matcher.group(2), "checkpoint") && run) {
                tmp = new Checkpoint(matcher.group(1), timeStamp, latitude, longitude);
                if(!containInList(tmp)) {
                    checkpointList.add(tmp);
                    DisplayToast.displayToast(context,"Le QrCode à bien été Scanné");
                }else {
                    DisplayToast.displayToast(context, "Le QrCode à déjà été Scanné");
                }
            } else if (Objects.equals(matcher.group(2), "end") && run) {
                tmp = new Checkpoint(matcher.group(1), timeStamp, latitude, longitude);
                if(!containInList(tmp)) {
                    checkpointList.add(tmp);
                    DisplayToast.displayToast(context,"Le QrCode à bien été Scanné");
                    WriteCsv.writeCourse(CheckPointManager.listToString());
                    MailDialog mailDialog = new MailDialog();
                    mailDialog.showDilaog(context);
                }else {
                    DisplayToast.displayToast(context, "Le QrCode à déjà été Scanné");
                }
                run = false;
            }

        }else {
            DisplayToast.displayToast(context, "Le QrCode n'est pas au bon format");
        }

    }

    public static boolean containInList(Checkpoint point){
        for (Checkpoint checkpoint: checkpointList) {
            if (Objects.equals(checkpoint.getIdBalise(), point.getIdBalise())){
                return true;
            }
        }
        return false;
    }

    public static List<Checkpoint> getCheckpointList() {
        return checkpointList;
    }

    public static void generateQr(int number, String mail, Context context) throws WriterException {

        Date date;
        date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-hh-mm");
        String folderName = formatter.format(date);

        Bitmap QR;
        QR = QrConverter.TextToImageEncode("#Depart#start#",500);
        QrConverter.saveImage(context,"start",0,QR,folderName);

        for (int i = 1; i<=number; i++) {
            QR = QrConverter.TextToImageEncode("#" + i + "#checkpoint#", 500);
            QrConverter.saveImage(context, "checkpoint", i, QR, folderName);
        }

        QR = QrConverter.TextToImageEncode("#Arrivé#end#"+mail,500);
        QrConverter.saveImage(context,"end",0,QR,folderName);

        QrConverter.zipFolder(Environment.getExternalStorageDirectory()+ "/QrCode/"+folderName, Environment.getExternalStorageDirectory()+ "/QrCode/"+folderName+".zip");

        DisplayToast.displayToast(context,"Les QrCode ont tous été créer");

        MailSend.sendQrMail(context,"guillaume.colletaz01@gmail.com",folderName);
    }

    public static String listToString(){
        StringBuilder checkpointToCSV = new StringBuilder();
        checkpointToCSV.append("Balise,");
        checkpointToCSV.append("Temp,");
        checkpointToCSV.append("Latitude,");
        checkpointToCSV.append("Longitude\n");
        for (Checkpoint checkpoint: checkpointList) {
            checkpointToCSV.append(checkpoint.getIdBalise());
            checkpointToCSV.append(',');
            checkpointToCSV.append(checkpoint.getTime());
            checkpointToCSV.append(',');
            checkpointToCSV.append(checkpoint.getLatitude());
            checkpointToCSV.append(',');
            checkpointToCSV.append(checkpoint.getLongitude());
            checkpointToCSV.append("\n");
        }
        return checkpointToCSV.toString();
    }

}
