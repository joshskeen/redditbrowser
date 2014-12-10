package com.joshskeen.redditbrowser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import timber.log.Timber;


public abstract class SingleFragmentActivity extends BaseActivity {

    private FrameLayout mFrameLayout;
    private View mViewById;

    public abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("ello g?");
        setContentView(R.layout.single_fragment_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_support_toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            mFrameLayout = (FrameLayout) findViewById(R.id.activity_single_fragment_frame_layout);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_single_fragment_frame_layout, createFragment())
                    .commit();
        }
    }

}