package com.joshskeen.redditbrowser.service;

import com.joshskeen.redditbrowser.model.PostsResponse;
import com.joshskeen.redditbrowser.model.response.AccessToken;
import com.joshskeen.redditbrowser.util.AuthUtil;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

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

    public void asyncGetAllTopPosts(Callback<PostsResponse> postsResponseCallback) {
        mRedditAPI.asyncGetAllPosts(postsResponseCallback);
    }
}
