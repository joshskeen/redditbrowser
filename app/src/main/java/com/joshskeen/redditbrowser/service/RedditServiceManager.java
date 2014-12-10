package com.joshskeen.redditbrowser.service;

import com.joshskeen.redditbrowser.ServiceDataManager;
import com.joshskeen.redditbrowser.model.Post;
import com.joshskeen.redditbrowser.model.response.AccessToken;
import com.joshskeen.redditbrowser.service.oauth.RedditOauthAccessTokenService;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class RedditServiceManager {

    private RedditOauthAccessTokenService mOauthAccessTokenService;
    private ServiceDataManager mServiceDataManager;
    private Observable<AccessToken> mAccessTokenObservable;

    public RedditServiceManager(
            RedditOauthAccessTokenService oauthAccessTokenService,
            ServiceDataManager serviceDataManager) {
        mOauthAccessTokenService = oauthAccessTokenService;
        mServiceDataManager = serviceDataManager;
    }

    private Observable<RedditService> getRedditService() {
        return getAccessTokenObservable().map(new Func1<AccessToken, RedditService>() {
            @Override
            public RedditService call(AccessToken accessToken) {
                return RedditService.getInstance(accessToken);
            }
        });
    }

    public void resetAuthToken() {
        mAccessTokenObservable = null;
        mServiceDataManager.setAccessToken(null);
    }

    public Observable<List<Post>> getTopPosts() {
        return getRedditService().flatMap(new Func1<RedditService, Observable<List<Post>>>() {
            @Override
            public Observable<List<Post>> call(RedditService redditService) {
                return redditService.rxGetRandomTopPosts();
            }
        });
    }

    public Observable<AccessToken> getAccessTokenObservable() {
        if (mAccessTokenObservable != null) {
            return mAccessTokenObservable;
        }
        return mOauthAccessTokenService.rxGetAccessToken().cache();
    }

}
