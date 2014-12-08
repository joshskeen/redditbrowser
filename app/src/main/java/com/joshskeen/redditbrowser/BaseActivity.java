package com.joshskeen.redditbrowser;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.joshskeen.redditbrowser.inject.SwipeToRefreshApplication;

public abstract class BaseActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeToRefreshApplication.get(this).inject(this);
    }

}
