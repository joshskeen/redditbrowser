package com.joshskeen.redditbrowser.service.oauth;

import com.joshskeen.redditbrowser.ServiceDataManager;
import com.joshskeen.redditbrowser.SharedPreferencesManager;
import com.joshskeen.redditbrowser.event.TokenLoadedEvent;
import com.joshskeen.redditbrowser.model.DeviceInfoManager;
import com.joshskeen.redditbrowser.model.response.AccessToken;
import com.joshskeen.redditbrowser.service.RedditAPICallback;

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
