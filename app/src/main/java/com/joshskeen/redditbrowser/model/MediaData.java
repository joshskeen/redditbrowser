package com.joshskeen.redditbrowser.model;

import android.content.Context;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.joshskeen.redditbrowser.util.TestUtils;

public class MediaData {

    @SerializedName("thumbnail_url")
    public String mThumbnailUrl;

    public void loadImage(Context context, ImageView imageView) {
        if (mThumbnailUrl != null) {
            TestUtils.picassoInstance(context).load(mThumbnailUrl).into(imageView);
        }
    }

    @Override
    public String toString() {
        return "MediaData{" +
                "mThumbnailUrl='" + mThumbnailUrl + '\'' +
                '}';
    }
}
