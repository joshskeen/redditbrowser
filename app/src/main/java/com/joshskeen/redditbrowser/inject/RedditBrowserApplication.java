package com.joshskeen.redditbrowser.inject;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;
import timber.log.Timber;

public class RedditBrowserApplication extends Application {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create(new RedditBrowserApplicationModule(this));
        Timber.plant(new Timber.DebugTree());
    }

    public static RedditBrowserApplication get(Context context) {
        return (RedditBrowserApplication) context.getApplicationContext();
    }

    public final void inject(Object object) {
        mObjectGraph.inject(object);
    }

}
