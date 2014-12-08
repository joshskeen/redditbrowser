package com.joshskeen.swipetorefresh.service;


import com.joshskeen.swipetorefresh.event.RetrofitErrorEvent;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;

public abstract class RedditAPICallback<T> implements Callback<T> {


    @Override
    public void failure(RetrofitError error) {
        EventBus.getDefault().post(new RetrofitErrorEvent(error));
    }

}
