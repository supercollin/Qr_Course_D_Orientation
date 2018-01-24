package com.android.dev.qrcoursedorientation.utils;

import android.os.Environment;
import android.util.Log;

import com.android.dev.qrcoursedorientation.models.Course;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by iem on 19/01/2018.
 */

public class FileWriter {

    public static void fileWriter (List<Course> courses) throws IOException {
        File directory = (new File(Environment.getExternalStorageDirectory()+ "/CourseList"));
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(directory, "CourseList.ser");
        file.createNewFile();
        Log.d("fileWriter: ",Environment.getDataDirectory().getPath());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(courses);
        objectOutputStream.close();
    }
}
