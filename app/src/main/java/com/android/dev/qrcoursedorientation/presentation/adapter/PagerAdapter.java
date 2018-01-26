package com.android.dev.qrcoursedorientation.presentation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by iem on 17/01/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter{


    private final List<Fragment> fragments;

    //On fournit à l'adapter la liste des fragments à afficher
    public PagerAdapter(FragmentManager fragmentManager, List fragments) {
        super(fragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
