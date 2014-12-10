package com.joshskeen.redditbrowser.service;

import com.joshskeen.redditbrowser.model.Post;
import com.joshskeen.redditbrowser.model.PostsResponse;
import com.joshskeen.redditbrowser.model.response.AccessToken;
import com.joshskeen.redditbrowser.util.AuthUtil;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rx.Observable;
import rx.functions.Func1;

public class RedditService {

    public static final String AUTHORIZATION = "Authorization";
    private RedditAPI mRedditAPI;
    private static String REDDIT_API_ENDPOINT = "https://oauth.reddit.com";

    public static RedditService getInstance(final AccessToken accessToken) {
        RedditAPI redditAPI = new RestAdapter.Builder()
                .setEndpoint(REDDIT_API_ENDPOINT)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        System.out.println(message);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader(AUTHORIZATION, AuthUtil.encodeCredentialsForRedditApiRequest(accessToken.mAccessToken));
                    }
                })
                .build()
                .create(RedditAPI.class);
        return new RedditService(redditAPI);
    }

    private RedditService(RedditAPI redditAPI) {
        mRedditAPI = redditAPI;
    }

    public PostsResponse getAllPosts() {
        return mRedditAPI.getAllPosts();
    }

    public PostsResponse getTopPosts(String subreddit) {
        return mRedditAPI.getTopPosts(subreddit);
    }

    public Observable<List<Post>> rxGetRandomTopPosts() {
        Observable<PostsResponse> postsResponseObservable = mRedditAPI.rxGetRandomTopPosts();
        return postsResponseObservable.map(new Func1<PostsResponse, List<Post>>() {
            @Override
            public List<Post> call(PostsResponse postsResponse) {
                return postsResponse.mResponseData.mPosts;
            }
        });
    }

    public void asyncGetAllTopPosts(Callback<PostsResponse> postsResponseCallback) {
        mRedditAPI.asyncGetAllPosts(postsResponseCallback);
    }
}
