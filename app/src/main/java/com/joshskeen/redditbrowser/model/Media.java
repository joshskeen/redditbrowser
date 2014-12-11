package com.joshskeen.redditbrowser.model;

import android.content.Context;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.joshskeen.redditbrowser.util.TestUtils;

public class Media {

    @SerializedName("oembed")
    public MediaData mMediaData;

    public String mThumbnailUrl;

    public void loadImage(Context context, ImageView imageView) {
        if (mMediaData != null) {
            mMediaData.loadImage(context, imageView);
        }
    }

    public boolean hasImage(){
        if(mThumbnailUrl != null){
            return TestUtils.isImage(mThumbnailUrl);
        }
        return mMediaData.hasImage();
    }

    @Override
    public String toString() {
        return "Media{" +
                "mMediaData=" + mMediaData +
                ", mThumbnailUrl='" + mThumbnailUrl + '\'' +
                '}';
    }
}
