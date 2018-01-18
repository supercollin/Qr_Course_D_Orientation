package com.android.dev.qrcoursedorientation.presentation.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.dev.qrcoursedorientation.R;

import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button buttonCreateCourse = findViewById(R.id.buttonCreateCourse) ;
        Button buttonRunCourse = findViewById(R.id.buttonRunCourse) ;
        buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonRunCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, BaseActivity.class);
                startActivity(intent);

            }
        });
    }
}


