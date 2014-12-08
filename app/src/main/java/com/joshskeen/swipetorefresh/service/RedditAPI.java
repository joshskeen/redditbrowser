package com.joshskeen.swipetorefresh.service;

import com.joshskeen.swipetorefresh.model.PostsResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface RedditAPI {

    @GET("/r/{subreddit}/top")
    public PostsResponse getTopPosts(@Path("subreddit") String subreddit);

    @GET("/r/all/top")
    PostsResponse getAllPosts();

    @GET("/r/random/top")
    void asyncGetAllPosts(Callback<PostsResponse> postsResponseCallback);

}
