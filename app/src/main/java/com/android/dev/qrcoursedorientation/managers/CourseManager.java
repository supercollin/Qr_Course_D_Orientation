package com.android.dev.qrcoursedorientation.managers;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.android.dev.qrcoursedorientation.models.Checkpoint;
import com.android.dev.qrcoursedorientation.models.Course;
import com.android.dev.qrcoursedorientation.presentation.dialogs.MailDialog;
import com.android.dev.qrcoursedorientation.utils.WriteCsv;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Guillaume Colletaz on 23/01/2018.
 */

public class CourseManager {

    private static List<Course> courseList = new ArrayList<>();
    private static Course timeStampCourse = new Course("stop", SystemClock.elapsedRealtime(),"","");

    public static List<Course> getCourseList() {
        return courseList;
    }

    public static String idRunner;

    public static void setIdRunner(String idRunner) {
        CourseManager.idRunner = idRunner;
    }

    public static Course getCurrentCourse(){

        for (int i = 0; i<getCourseList().size(); i++){
            if(Objects.equals(getCourseList().get(i).getStatus(), "start")){
                return getCourseList().get(i);
            }
        }
        return timeStampCourse;
    }

    public static void setCourseList(Course course) {
        CourseManager.courseList.add(course);
    }

    public static void setCourseListFromList(List<Course> courses) {
        CourseManager.courseList = courses;
    }

    public static void createCourse(Context context, String qrResuult,long timeStamp){
        String mail;
        Course course;
        Checkpoint checkpoint;

        String res = "";
        String patternStr = "#([\\w\\W]*)#([\\w\\W]*)#([\\w\\W]*)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(qrResuult);
        boolean matchFound = matcher.find();
        if (matchFound && matcher.groupCount() >= 1) {
            if (Objects.equals(matcher.group(2), "start")) {
                mail = matcher.group(3);
                course = new Course("start",timeStamp,mail,idRunner);
                setCourseList(course);
                checkpoint = CheckPointManager.getLastCheckpoint();
                getCourseList().get(getCourseList().size()-1).getCheckpointList().add(checkpoint);
            }else if (Objects.equals(matcher.group(2), "end")) {
                for (int i = 0; i < getCourseList().size(); i++){
                    if (Objects.equals(getCourseList().get(i).getStatus(), "start")){
                        getCourseList().get(i).setStatus("stop");
                        checkpoint = CheckPointManager.getLastCheckpoint();
                        getCourseList().get(getCourseList().size()-1).getCheckpointList().add(checkpoint);
                        WriteCsv.writeCourse(CheckPointManager.listToString());
                        MailDialog mailDialog = new MailDialog();
                        mailDialog.showDialog(context);
                    }
                }
                CheckPointManager.cleanCheckpointManager();
            }else if (Objects.equals(matcher.group(2), "checkpoint")) {
                checkpoint = CheckPointManager.getLastCheckpoint();
                getCourseList().get(getCourseList().size()-1).getCheckpointList().add(checkpoint);
            }
        }
    }
}
