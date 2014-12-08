package com.joshskeen.swipetorefresh.util;

import android.util.Base64;

import com.joshskeen.swipetorefresh.BuildConfig;

public class AuthUtil {

    //used with RedditOauth Access Token Retrieval API
    public static String encodeCredentialsForOauthAccessRequest() {
        //no secret passed for installed client apps according to reddit api, so just the client id is passed in the header
        final String credentials = BuildConfig.REDDIT_CLIENT_ID + ":";
        return "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    }

    //used with Reddit API
    public static String encodeCredentialsForRedditApiRequest(String accessToken) {
        return "bearer " + accessToken;
    }

}
