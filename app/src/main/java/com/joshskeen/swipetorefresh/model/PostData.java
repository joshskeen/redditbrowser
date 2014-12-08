package com.joshskeen.swipetorefresh.model;

import com.google.gson.annotations.SerializedName;

public class PostData {

    @SerializedName("title")
    public String mTitle;

    @SerializedName("url")
    public String mUrl;

    @SerializedName("selftext_html")
    public String mSelfText;

    @SerializedName("thumbnail")
    public String mThumbnail;

    @SerializedName("subreddit")
    public String mSubbreddit;

    @SerializedName("media")
    public Media mMedia;

    @Override
    public String toString() {
        return "PostData{" +
                "mTitle='" + mTitle + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mSelfText='" + mSelfText + '\'' +
                ", mThumbnail='" + mThumbnail + '\'' +
                ", mMedia=" + mMedia +
                '}';
    }
}
