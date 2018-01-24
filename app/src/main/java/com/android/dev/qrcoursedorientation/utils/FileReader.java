package com.android.dev.qrcoursedorientation.utils;

import android.os.Environment;

import com.android.dev.qrcoursedorientation.models.Course;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Created by iem on 19/01/2018.
 */

public class FileReader {
    public static List<Course> fileReader () throws IOException, ClassNotFoundException {
        File file = (new File(Environment.getExternalStorageDirectory()+ "/CourseList/CourseList.ser"));
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        List<Course> course = (List<Course>) objectInputStream.readObject();
        objectInputStream.close();
        return course;
    }
}
