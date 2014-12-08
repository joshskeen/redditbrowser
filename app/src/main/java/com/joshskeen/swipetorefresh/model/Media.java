package com.joshskeen.swipetorefresh.model;

import android.content.Context;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

public class Media {

    @SerializedName("oembed")
    public MediaData mMediaData;

    public String mThumbnailUrl;

    public void loadImage(Context context, ImageView imageView) {
        if (mMediaData != null) {
            mMediaData.loadImage(context, imageView);
        }
    }

    @Override
    public String toString() {
        return "Media{" +
                "mMediaData=" + mMediaData +
                ", mThumbnailUrl='" + mThumbnailUrl + '\'' +
                '}';
    }
}
