package com.joshskeen.redditbrowser.event;

import retrofit.RetrofitError;

public class RetrofitErrorEvent {
    private RetrofitError mRetrofitError;

    public RetrofitErrorEvent(RetrofitError retrofitError) {
        mRetrofitError = retrofitError;
    }
}
