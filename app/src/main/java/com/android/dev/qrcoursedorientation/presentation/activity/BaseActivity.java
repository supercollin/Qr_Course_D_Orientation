package com.android.dev.qrcoursedorientation.presentation.activity;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.android.dev.qrcoursedorientation.R;
import com.android.dev.qrcoursedorientation.presentation.fragment.QrCheckpointListFragment;
import com.android.dev.qrcoursedorientation.presentation.fragment.QrFragment;
import com.android.dev.qrcoursedorientation.presentation.adapter.PagerAdapter;
import com.android.dev.qrcoursedorientation.presentation.transformers.ZoomOutPageTransformer;

import java.util.List;
import java.util.Vector;


public class BaseActivity extends FragmentActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Création de la liste de Fragments que fera défiler le PagerAdapter
        List fragments = new Vector();

        // Ajout des Fragments dans la liste
        fragments.add(Fragment.instantiate(this,QrFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,QrCheckpointListFragment.class.getName()));

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = findViewById(R.id.pager);
        // Affectation de l'adapter au ViewPager
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }



//    public void displayQRView(){
//        qrNavigator.launchQrFragment();
//    }

//    public void displayQRCheckpointListView(){
//        qrNavigator.launchQrCheckpointListFragment();
//    }



}
