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
        }).cache();
    }

    public Observable<List<Post>> getTopPostsWithPicsOnly() {
        Observable<List<Post>> listObservable = filteredTopPosts();
        filteredTopPosts().map(new Func1<List<Post>, Observable<List<Post>>>() {
            @Override
            public Observable<List<Post>> call(List<Post> posts) {
                if (posts.isEmpty()) {
                    return getTopPostsWithPicsOnly();
                }
                return Observable.just(posts);
            }
        });
        return listObservable;
    }

    private Observable<List<Post>> filteredTopPosts() {
        return getTopPosts().map(new Func1<List<Post>, List<Post>>() {
            @Override
            public List<Post> call(List<Post> posts) {
                return posts;
            }
        }).flatMap(new Func1<List<Post>, Observable<Post>>() {
            @Override
            public Observable<Post> call(List<Post> posts) {
                return Observable.from(posts);
            }
        }).filter(new Func1<Post, Boolean>() {
            @Override
            public Boolean call(Post post) {
                return post.hasImage();
            }
        }).toList();
    }


    public Observable<AccessToken> getAccessTokenObservable() {
        if (mAccessTokenObservable != null) {
            return mAccessTokenObservable;
        }
        return mOauthAccessTokenService.rxGetAccessToken().cache();
    }

}
