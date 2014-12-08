package com.joshskeen.swipetorefresh.inject;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;

public class SwipeToRefreshApplication extends Application {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create(new SwipeToRefreshApplicationModule(this));
    }

    public static SwipeToRefreshApplication get(Context context) {
        return (SwipeToRefreshApplication) context.getApplicationContext();
    }

    public final void inject(Object object) {
        mObjectGraph.inject(object);
    }

}
