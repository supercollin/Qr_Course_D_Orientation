package com.android.dev.qrcoursedorientation.presentation.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.managers.CheckPointManager;
import com.android.dev.qrcoursedorientation.managers.CourseManager;
import com.android.dev.qrcoursedorientation.models.Course;
import com.android.dev.qrcoursedorientation.presentation.component.DisplayToast;
import com.android.dev.qrcoursedorientation.presentation.dialogs.AlreadyCourseIsRunDialog;
import com.android.dev.qrcoursedorientation.presentation.dialogs.CreateCourseDialog;
import com.android.dev.qrcoursedorientation.utils.FileReader;

import java.io.IOException;
import java.util.Objects;


public class StartActivity extends AppCompatActivity {

    CreateCourseDialog createCourseDialog;

    Button buttonCreateCourse;
    Button buttonRunCourse;
    Button buttonChronicle;
    Button buttonRestartCourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        buttonCreateCourse = findViewById(R.id.buttonCreateCourse) ;
        buttonRunCourse = findViewById(R.id.buttonRunCourse) ;
        buttonChronicle = findViewById(R.id.buttonChronicle) ;
        buttonRestartCourse = findViewById(R.id.restartCourse);

        try {
            CourseManager.setCourseListFromList(FileReader.fileReader());
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(Objects.equals(CourseManager.getCurrentCourse().getStatus(), "start")){
            buttonRestartCourse.setVisibility(View.VISIBLE);
            Course course = CourseManager.getCurrentCourse();
            CheckPointManager.setCheckpointList(course.getCheckpointList());
            CheckPointManager.setTimeStampBase(course.getTimestamp());
            CheckPointManager.setRun(true);
        }else{
            buttonRestartCourse.setVisibility(View.INVISIBLE);
            CheckPointManager.cleanCheckpointManager();
        }

        buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCourseDialog = new CreateCourseDialog(StartActivity.this);
                createCourseDialog.show();
            }
        });

        buttonRunCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(CourseManager.getCurrentCourse().getStatus(), "start")){
                    AlreadyCourseIsRunDialog alreadyCourseIsRunDialog = new AlreadyCourseIsRunDialog();
                    alreadyCourseIsRunDialog.showDialog(StartActivity.this);
                }else{
                    Intent intent = new Intent(StartActivity.this,BaseActivity.class);
                    startActivity(intent);
                }
            }
        });

        buttonChronicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CourseManager.getCourseList().size()>0) {
                    Intent intent = new Intent(StartActivity.this, ChronicleActivity.class);
                    startActivity(intent);
                }else{
                    DisplayToast.displayToast(getBaseContext(),getString(R.string.toast_no_course));
                }
            }
        });

        buttonRestartCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,BaseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(Objects.equals(CourseManager.getCurrentCourse().getStatus(), "start")){
            buttonRestartCourse.setVisibility(View.VISIBLE);
            CheckPointManager.cleanCheckpointManager();
            Course course = CourseManager.getCurrentCourse();
            CheckPointManager.setCheckpointList(course.getCheckpointList());
            CheckPointManager.setTimeStampBase(course.getTimestamp());
            CheckPointManager.setRun(true);
        }else{
            buttonRestartCourse.setVisibility(View.INVISIBLE);
            CheckPointManager.cleanCheckpointManager();
        }
    }
}


