package com.joshskeen.swipetorefresh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostsResponseData {

    @SerializedName("children")
    public List<Post> mPosts;

    @Override
    public String toString() {
        return "PostsResponseData{" +
                "mPosts=" + mPosts +
                '}';
    }
}
