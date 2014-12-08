package com.joshskeen.swipetorefresh.service;

import com.joshskeen.swipetorefresh.ServiceDataManager;
import com.joshskeen.swipetorefresh.event.PostsLoadedEvent;
import com.joshskeen.swipetorefresh.model.Post;
import com.joshskeen.swipetorefresh.model.PostsResponse;

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
