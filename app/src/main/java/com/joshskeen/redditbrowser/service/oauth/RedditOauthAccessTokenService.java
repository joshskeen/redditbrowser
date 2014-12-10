package com.joshskeen.redditbrowser.service.oauth;

import android.content.Context;

import com.joshskeen.redditbrowser.ServiceDataManager;
import com.joshskeen.redditbrowser.model.response.AccessToken;
import com.joshskeen.redditbrowser.util.AuthUtil;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Func1;

public class RedditOauthAccessTokenService {

    private static final String SERVICE_ENDPOINT = "https://ssl.reddit.com/api/v1";
    private static final String REDDIT_OAUTH_INSTALLED_CLIENT_GRANT = "https://oauth.reddit.com/grants/installed_client";
    public static final String AUTHORIZATION = "Authorization";

    private final String mDeviceId;
    private ServiceDataManager mServiceDataManager;
    private Context mContext;
    private RedditOauthAccessTokenAPI mRedditOauthAPI;

    private RedditOauthAccessTokenService(ServiceDataManager serviceDataManager, Context context, String deviceId, RedditOauthAccessTokenAPI redditOauthAPI) {
        mContext = context;
        mRedditOauthAPI = redditOauthAPI;
        mDeviceId = deviceId;
        mServiceDataManager = serviceDataManager;
    }

    public static RedditOauthAccessTokenService getInstance(ServiceDataManager serviceDataManager, Context context, String deviceId) {
        RedditOauthAccessTokenAPI redditOauthAPI = new RestAdapter.Builder()
                .setEndpoint(SERVICE_ENDPOINT)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader(AUTHORIZATION, AuthUtil.encodeCredentialsForOauthAccessRequest());
                    }
                })
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        System.out.println("ERROR: " + cause);
                        return cause;
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
        return new RedditOauthAccessTokenService(serviceDataManager, context, deviceId, redditOauthAPI);
    }

    public void asyncGetAccessToken(Callback<AccessToken> callback) {
        mRedditOauthAPI.getAsyncInstalledClientGrant(REDDIT_OAUTH_INSTALLED_CLIENT_GRANT, mDeviceId, callback);
    }

    public AccessToken requestAccessToken() {
        AccessToken first = mRedditOauthAPI.rxGetAccessToken(REDDIT_OAUTH_INSTALLED_CLIENT_GRANT, mDeviceId).toBlocking().first();
        return first;
    }

    public Observable<AccessToken> rxGetAccessToken() {
        final Observable<AccessToken> accessTokenObservable = Observable.just(mServiceDataManager.getAccessToken());
        return accessTokenObservable.flatMap(new Func1<AccessToken, Observable<AccessToken>>() {
            @Override
            public Observable<AccessToken> call(AccessToken accessToken) {
                if (accessToken != null) {
                    return accessTokenObservable;
                }
                return mRedditOauthAPI.rxGetAccessToken(REDDIT_OAUTH_INSTALLED_CLIENT_GRANT, mDeviceId);
            }
        });
    }

}
