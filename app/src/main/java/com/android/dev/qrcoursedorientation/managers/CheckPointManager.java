package com.android.dev.qrcoursedorientation.managers;

import android.util.Log;

import com.android.dev.qrcoursedorientation.models.Checkpoint;

import java.util.ArrayList;
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

    private static boolean run = false;

    public static void setTimeStamp(String time){
        timeStamp = time;
    }

    public static boolean isRun() {
        return run;
    }

    public static void createCheckPoint(String qrResuult, int longitude, int latitude) {

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

            if (Objects.equals(matcher.group(2), "start") && checkpointList.size() == 0) {
                run = true;
            } else if (Objects.equals(matcher.group(2), "checkpoint") && run) {
                tmp = new Checkpoint(matcher.group(1), timeStamp, latitude, longitude);
                checkpointList.add(tmp);
            } else if (Objects.equals(matcher.group(2), "end") && run) {
                tmp = new Checkpoint(matcher.group(1), timeStamp, latitude, longitude);
                checkpointList.add(tmp);
                run = false;
            }
        }

    }



    public static List<Checkpoint> getCheckpointList() {
        return checkpointList;
    }
}
