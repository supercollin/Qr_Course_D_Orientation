package com.android.dev.qrcoursedorientation.managers;

import android.util.Log;

import com.android.dev.qrcoursedorientation.models.Checkpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Guillaume Colletaz on 17/01/2018.
 */

public class CheckPointManager {


    private static List<Checkpoint> qrCheckpointListViewModels = new ArrayList<>();

    public static void createCheckPoint(String qrResuult, String time, int longitude, int latitude) {

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
        }

        if(matcher.group(2) != "end"){
            tmp = new Checkpoint(matcher.group(1),time,latitude,longitude);
            qrCheckpointListViewModels.add(tmp);
        }

    }
}
