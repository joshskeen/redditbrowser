package com.joshskeen.redditbrowser.model;

import com.google.gson.annotations.SerializedName;

public class PostsResponse {

    @SerializedName("data")
    public PostsResponseData mResponseData;

    @Override
    public String toString() {
        return "PostsResponse{" +
                "mResponseData=" + mResponseData +
                '}';
    }
}
