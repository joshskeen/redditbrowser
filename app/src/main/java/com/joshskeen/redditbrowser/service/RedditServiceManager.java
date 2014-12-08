package com.joshskeen.redditbrowser.service;

import com.joshskeen.redditbrowser.ServiceDataManager;
import com.joshskeen.redditbrowser.event.PostsLoadedEvent;
import com.joshskeen.redditbrowser.model.Post;
import com.joshskeen.redditbrowser.model.PostsResponse;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.client.Response;

public class RedditServiceManager {

    private RedditService mRedditService;
    private ServiceDataManager mServiceDataManager;

    public RedditServiceManager(RedditService redditService, ServiceDataManager serviceDataManager) {
        mRedditService = redditService;
        mServiceDataManager = serviceDataManager;
    }

    public void loadTopPosts() {
        mRedditService = RedditService.getInstance(mServiceDataManager.getAccessToken());
        mRedditService.asyncGetAllTopPosts(new RedditAPICallback<PostsResponse>() {
            @Override
            public void success(PostsResponse postsResponse, Response response) {
                List<Post> posts = postsResponse.mResponseData.mPosts;
                mServiceDataManager.setPosts(posts);
                EventBus.getDefault().post(new PostsLoadedEvent());
            }
        });
    }

}
