package com.joshskeen.swipetorefresh.service.oauth;

import com.joshskeen.swipetorefresh.ServiceDataManager;
import com.joshskeen.swipetorefresh.SharedPreferencesManager;
import com.joshskeen.swipetorefresh.event.TokenLoadedEvent;
import com.joshskeen.swipetorefresh.model.DeviceInfoManager;
import com.joshskeen.swipetorefresh.model.response.AccessToken;
import com.joshskeen.swipetorefresh.service.RedditAPICallback;

import de.greenrobot.event.EventBus;
import retrofit.client.Response;

public class RedditOauthAccessTokenServiceManager {

    private RedditOauthAccessTokenService mRedditOauthService;
    private ServiceDataManager mServiceDataManager;
    private DeviceInfoManager mDeviceInfoManager;
    private SharedPreferencesManager mSharedPreferencesManager;

    public RedditOauthAccessTokenServiceManager(RedditOauthAccessTokenService redditOauthService,
                                                ServiceDataManager serviceDataManager,
                                                DeviceInfoManager deviceInfoManager) {
        mRedditOauthService = redditOauthService;
        mServiceDataManager = serviceDataManager;
        mDeviceInfoManager = deviceInfoManager;
    }

    public void loadAccessToken() {
        mRedditOauthService.asyncGetAccessToken(new RedditAPICallback<AccessToken>() {
            @Override
            public void success(AccessToken accessToken, Response response) {
                mServiceDataManager.setAccessToken(accessToken);
                EventBus.getDefault().post(new TokenLoadedEvent(accessToken));
            }
        });
    }
}
