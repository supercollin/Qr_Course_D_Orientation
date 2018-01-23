package com.android.dev.qrcoursedorientation.utils;

import android.os.Environment;

import com.android.dev.qrcoursedorientation.models.Course;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by iem on 19/01/2018.
 */

public class FileReader {
    public static Course fileReader () throws IOException, ClassNotFoundException {
        File file = (new File(Environment.getDataDirectory()+ "/CourseList.ser"));
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        Course course = (Course) objectInputStream.readObject();
        objectInputStream.close();
        return course;
    }
}