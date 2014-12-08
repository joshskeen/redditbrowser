package com.joshskeen.swipetorefresh.inject;

import com.joshskeen.swipetorefresh.MainActivity;
import com.joshskeen.swipetorefresh.ServiceDataManager;
import com.joshskeen.swipetorefresh.SharedPreferencesManager;
import com.joshskeen.swipetorefresh.SplashActivity;
import com.joshskeen.swipetorefresh.SwipeRefreshExampleFragment;
import com.joshskeen.swipetorefresh.model.DeviceInfoManager;
import com.joshskeen.swipetorefresh.service.RedditService;
import com.joshskeen.swipetorefresh.service.RedditServiceManager;
import com.joshskeen.swipetorefresh.service.oauth.RedditOauthAccessTokenService;
import com.joshskeen.swipetorefresh.service.oauth.RedditOauthAccessTokenServiceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {SplashActivity.class, MainActivity.class, SwipeRefreshExampleFragment.class}, includes = {})
public class SwipeToRefreshApplicationModule {

    private SwipeToRefreshApplication mApplication;

    public SwipeToRefreshApplicationModule(SwipeToRefreshApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public ServiceDataManager getDataManager(SharedPreferencesManager sharedPreferencesManager) {
        return new ServiceDataManager(mApplication, sharedPreferencesManager);
    }

    @Provides
    @Singleton
    public SharedPreferencesManager getSharedPreferencesManager() {
        return new SharedPreferencesManager(mApplication);
    }

    @Provides
    @Singleton
    public DeviceInfoManager getDeviceInfoManager() {
        return new DeviceInfoManager(mApplication);
    }

    @Provides
    public RedditService getRedditService(ServiceDataManager serviceDataManager) {
        return RedditService.getInstance(serviceDataManager.getAccessToken());
    }

    @Provides
    public RedditServiceManager getRedditServiceManager(RedditService redditService, ServiceDataManager serviceDataManager) {
        return new RedditServiceManager(redditService, serviceDataManager);
    }

    @Provides
    @Singleton
    public RedditOauthAccessTokenServiceManager getRedditOauthAccessTokenServiceManager(
            ServiceDataManager serviceDataManager,
            DeviceInfoManager deviceInfoManager) {
        String deviceId = new DeviceInfoManager(mApplication).getDeviceId();
        RedditOauthAccessTokenService instance = RedditOauthAccessTokenService.getInstance(mApplication, deviceId);
        return new RedditOauthAccessTokenServiceManager(instance, serviceDataManager, deviceInfoManager);
    }


}
