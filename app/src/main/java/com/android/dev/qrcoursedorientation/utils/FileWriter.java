package com.android.dev.qrcoursedorientation.utils;

import android.os.Environment;

import com.android.dev.qrcoursedorientation.models.Course;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by iem on 19/01/2018.
 */

public class FileWriter {

    public static void fileWriter (Course course) throws IOException {
        File file = (new File(Environment.getDataDirectory()+ "/CourseList.ser"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(course);
        objectOutputStream.close();
    }
}
