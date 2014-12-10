package com.joshskeen.redditbrowser;

import android.content.Context;

import com.joshskeen.redditbrowser.model.Post;
import com.joshskeen.redditbrowser.model.response.AccessToken;

import java.util.List;

public class ServiceDataManager {

    private List<Post> mPosts;
    private Context mContext;
    private SharedPreferencesManager mSharedPreferencesManager;
    private AccessToken mAccessTokenResponse;

    public ServiceDataManager(Context context, SharedPreferencesManager sharedPreferencesManager) {
        mContext = context;
        mSharedPreferencesManager = sharedPreferencesManager;
    }

    public List<Post> getPosts() {
        return mPosts;
    }

    public void setPosts(List<Post> posts) {
        mPosts = posts;
    }

    public void setAccessToken(AccessToken accessTokenResponse) {
        mAccessTokenResponse = accessTokenResponse;
        mSharedPreferencesManager.saveAccessToken(mAccessTokenResponse);
    }

    public AccessToken getAccessToken() {
        if (mAccessTokenResponse == null && mSharedPreferencesManager.getAccessToken() != null) {
            mAccessTokenResponse = mSharedPreferencesManager.getAccessToken();
        }
        return mAccessTokenResponse;
    }

    public String getSectionTitle() {
        if (getPosts() != null && getPosts().size() > 0) {
            return "/r/" + getPosts().get(0).getSubbreddit();
        }
        return "/r/unknown";
    }
}
