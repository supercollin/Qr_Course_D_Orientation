package com.android.dev.qrcoursedorientation.presentation.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.managers.CourseManager;
import com.android.dev.qrcoursedorientation.presentation.dialogs.CreateCourseDialog;
import com.android.dev.qrcoursedorientation.utils.FileReader;
import com.android.dev.qrcoursedorientation.utils.FileWriter;

import java.io.IOException;


public class StartActivity extends AppCompatActivity {

    CreateCourseDialog createCourseDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button buttonCreateCourse = findViewById(R.id.buttonCreateCourse) ;
        Button buttonRunCourse = findViewById(R.id.buttonRunCourse) ;
        Button buttonChronicle = findViewById(R.id.buttonChronicle) ;
        createCourseDialog = new CreateCourseDialog(StartActivity.this);
        try {
            Log.d("onCreate: f ", FileReader.fileReader().toString());
            CourseManager.setCourseListFromList(FileReader.fileReader());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("onCreate: ", CourseManager.getCourseList().toString());
        buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCourseDialog.show();
            }
        });

        buttonRunCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,BaseActivity.class);
                startActivity(intent);
            }
        });

        buttonChronicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,ChronicleActivity.class);
                startActivity(intent);
            }
        });
    }
}


