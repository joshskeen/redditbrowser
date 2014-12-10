package com.joshskeen.redditbrowser.service;

import com.joshskeen.redditbrowser.model.PostsResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface RedditAPI {

    @GET("/r/{subreddit}/top")
    public PostsResponse getTopPosts(@Path("subreddit") String subreddit);

    @GET("/r/all/top")
    PostsResponse getAllPosts();

    @GET("/r/random/top")
    void asyncGetAllPosts(Callback<PostsResponse> postsResponseCallback);

    @GET("/r/random/top")
    Observable<PostsResponse> rxGetRandomTopPosts();
}
