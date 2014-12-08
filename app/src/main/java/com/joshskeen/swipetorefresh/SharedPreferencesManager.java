package com.joshskeen.swipetorefresh;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.joshskeen.swipetorefresh.model.response.AccessToken;

public class SharedPreferencesManager {

    public static final String SHAREDPREFS = "sharedprefs";
    private static final String ACCESS_TOKEN = "access_token";
    private final SharedPreferences mSharedPreferences;
    private Context mContext;

    public SharedPreferencesManager(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE);
    }

    public void saveAccessToken(AccessToken accessTokenResponse) {
        String accessToken = new Gson().toJson(accessTokenResponse);
        mSharedPreferences.edit().putString(ACCESS_TOKEN, accessToken).commit();
    }

    public AccessToken getAccessToken() {
        String accessTokenAsJson = mSharedPreferences.getString(ACCESS_TOKEN, null);
        if (accessTokenAsJson == null) {
            return null;
        }
        return new Gson().fromJson(accessTokenAsJson, AccessToken.class);
    }

}
