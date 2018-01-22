package com.android.dev.qrcoursedorientation.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Guillaume Colletaz on 22/01/2018.
 */

public class WriteCsv {

    public static void writeCourse(String data){

        Date date;
        date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-hh-mm");
        String folderName = formatter.format(date);

        final File path = new File(Environment.getExternalStorageDirectory() + "/Course/");
        if(!path.exists())
        {
            path.mkdirs();
        }
        final File file = new File(path, folderName+ ".csv");
        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
