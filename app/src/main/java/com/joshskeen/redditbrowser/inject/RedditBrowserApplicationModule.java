package com.joshskeen.redditbrowser.inject;

import com.joshskeen.redditbrowser.MainActivity;
import com.joshskeen.redditbrowser.RedditBrowserFragment;
import com.joshskeen.redditbrowser.ServiceDataManager;
import com.joshskeen.redditbrowser.SharedPreferencesManager;
import com.joshskeen.redditbrowser.SplashActivity;
import com.joshskeen.redditbrowser.model.DeviceInfoManager;
import com.joshskeen.redditbrowser.service.RedditServiceManager;
import com.joshskeen.redditbrowser.service.oauth.RedditOauthAccessTokenService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {SplashActivity.class, MainActivity.class, RedditBrowserFragment.class}, includes = {})
public class RedditBrowserApplicationModule {

    private RedditBrowserApplication mApplication;

    public RedditBrowserApplicationModule(RedditBrowserApplication application) {
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
    @Named("deviceId")
    public String getDeviceId(DeviceInfoManager deviceInfoManager) {
        return new DeviceInfoManager(mApplication).getDeviceId();
    }

    @Provides
    public RedditOauthAccessTokenService getRedditOauthAccessTokenService(ServiceDataManager serviceDataManager, @Named("deviceId") String deviceId) {
        return RedditOauthAccessTokenService.getInstance(serviceDataManager, mApplication, deviceId);
    }

    @Provides
    public RedditServiceManager getRedditServiceManager(RedditOauthAccessTokenService service, ServiceDataManager serviceDataManager) {
        return new RedditServiceManager(service, serviceDataManager);
    }

}
