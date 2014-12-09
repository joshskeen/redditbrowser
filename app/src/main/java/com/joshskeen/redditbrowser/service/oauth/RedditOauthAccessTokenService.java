package com.joshskeen.redditbrowser.service.oauth;

import android.content.Context;

import com.joshskeen.redditbrowser.model.response.AccessToken;
import com.joshskeen.redditbrowser.util.AuthUtil;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class RedditOauthAccessTokenService {

    private static final String SERVICE_ENDPOINT = "https://ssl.reddit.com/api/v1";
    private static final String REDDIT_OAUTH_INSTALLED_CLIENT_GRANT = "https://oauth.reddit.com/grants/installed_client";
    public static final String AUTHORIZATION = "Authorization";

    private final String mDeviceId;
    private Context mContext;
    private RedditOauthAccessTokenAPI mRedditOauthAPI;

    private RedditOauthAccessTokenService(Context context, String deviceId, RedditOauthAccessTokenAPI redditOauthAPI) {
        mContext = context;
        mRedditOauthAPI = redditOauthAPI;
        mDeviceId = deviceId;
    }

    public static RedditOauthAccessTokenService getInstance(Context context, String deviceId) {
        RedditOauthAccessTokenAPI redditOauthAPI = new RestAdapter.Builder()
                .setEndpoint(SERVICE_ENDPOINT)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader(AUTHORIZATION, AuthUtil.encodeCredentialsForOauthAccessRequest());
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        System.out.println(message);
                    }
                })
                .build().create(RedditOauthAccessTokenAPI.class);
        return new RedditOauthAccessTokenService(context, deviceId, redditOauthAPI);
    }

    public AccessToken getAccessToken() {
        return mRedditOauthAPI.getInstalledClientGrant(REDDIT_OAUTH_INSTALLED_CLIENT_GRANT, mDeviceId, "permanent");
    }

    public void asyncGetAccessToken(Callback<AccessToken> callback) {
        mRedditOauthAPI.getAsyncInstalledClientGrant(REDDIT_OAUTH_INSTALLED_CLIENT_GRANT, mDeviceId, callback);
    }

    public Observable<AccessToken> rxGetAccessToken() {
        return mRedditOauthAPI.rxGetAsyncInstalledClientGrant(REDDIT_OAUTH_INSTALLED_CLIENT_GRANT, mDeviceId);
    }
}
