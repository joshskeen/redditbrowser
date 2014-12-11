package com.joshskeen.redditbrowser.model;

import android.content.Context;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.joshskeen.redditbrowser.util.TestUtils;

public class Post {

    @SerializedName("data")
    public PostData mPostData;

    @Override
    public String toString() {
        return "Post{" +
                "mPostData=" + mPostData +
                '}';
    }

    public String getSubbreddit() {
        return mPostData.mSubbreddit;
    }

    public void displayImage(Context context, ImageView imageView) {
        if (mPostData.mMedia != null) {
            mPostData.mMedia.loadImage(context, imageView);
        } else if (TestUtils.isImage(mPostData.mUrl + "")) {
            TestUtils.picassoInstance(context).load(mPostData.mUrl).into(imageView);
        } else if (TestUtils.isImage(mPostData.mThumbnail + "")) {
            TestUtils.picassoInstance(context).load(mPostData.mThumbnail).into(imageView);
        }
    }

    public Boolean hasImage() {
        if(TestUtils.isImage(mPostData.mThumbnail) || TestUtils.isImage(mPostData.mUrl)){
            return true;
        }
        if (mPostData.mMedia != null) {

        }
        return false;
    }
}
