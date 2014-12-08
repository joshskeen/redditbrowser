package com.joshskeen.redditbrowser;

import android.content.Intent;
import android.os.Bundle;

import com.joshskeen.redditbrowser.event.TokenLoadedEvent;
import com.joshskeen.redditbrowser.service.oauth.RedditOauthAccessTokenServiceManager;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;


public class SplashActivity extends BaseActivity {

    @Inject
    ServiceDataManager mServiceDataManager;
    @Inject
    RedditOauthAccessTokenServiceManager mRedditOauthAccessTokenServiceManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mServiceDataManager.getAccessToken() == null) {
            EventBus.getDefault().register(this);
            mRedditOauthAccessTokenServiceManager.loadAccessToken();
        } else {
            startMainActivity();
        }
    }

    private void startMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(0, 0);
    }

    public void onEvent(TokenLoadedEvent event) {
        startMainActivity();
    }

    protected void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

}
