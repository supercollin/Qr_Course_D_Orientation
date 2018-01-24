package com.android.dev.qrcoursedorientation.presentation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.android.dev.qrcoursedorientation.managers.CourseManager;
import com.android.dev.qrcoursedorientation.models.Checkpoint;
import com.android.dev.qrcoursedorientation.models.Course;
import com.android.dev.qrcoursedorientation.presentation.fragment.CourseCardFragment;
import com.android.dev.qrcoursedorientation.presentation.viewmodel.QrCheckpointViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by iem on 22/01/2018.
 */

public class CourseCardFragmentAdapter extends FragmentStatePagerAdapter implements CourseCardAdapter {

        private List<CourseCardFragment> mFragments;
        private float mBaseElevation;

        public CourseCardFragmentAdapter(FragmentManager fm, float baseElevation) {
            super(fm);
            mFragments = new ArrayList<>();
            mBaseElevation = baseElevation;
            List<Course> courseList = CourseManager.getCourseList();
            for(int i = 0; i< courseList.size(); i++){
                    addCardFragment(new CourseCardFragment());
                    List<QrCheckpointViewModel> checkpointViewModels = new ArrayList<>();
                    List<Checkpoint> checkpointList = courseList.get(courseList.size() - 1 - i).getCheckpointList();
                    for (int j = 0; j < checkpointList.size(); j++) {
                        checkpointViewModels.add(new QrCheckpointViewModel(checkpointList.get(j)));
                    }
                    mFragments.get(i).setCheckpointViewModels(checkpointViewModels);
            }
        }

        @Override
        public float getBaseElevation() {
            return mBaseElevation;
        }


        @Override
        public CardView getCardViewAt(int position) {
            return mFragments.get(position).getCardView();
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object fragment = super.instantiateItem(container, position);
            mFragments.set(position, (CourseCardFragment) fragment);
            return fragment;
        }

        public void addCardFragment(CourseCardFragment fragment) {
            mFragments.add(fragment);
        }


}
