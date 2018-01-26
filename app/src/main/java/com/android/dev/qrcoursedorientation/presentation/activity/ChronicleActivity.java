package com.android.dev.qrcoursedorientation.presentation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.presentation.adapter.CourseCardFragmentAdapter;
import com.android.dev.qrcoursedorientation.presentation.transformers.ShadowTransformer;

/**
 * Created by iem on 22/01/2018.
 */

public class ChronicleActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CourseCardFragmentAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronicle);
        this.setTitle(R.string.chronicle_activity_title);
        mViewPager = findViewById(R.id.viewPager);

        mFragmentCardAdapter = new CourseCardFragmentAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mFragmentCardAdapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mFragmentCardShadowTransformer.enableScaling(true);
    }



    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

}
