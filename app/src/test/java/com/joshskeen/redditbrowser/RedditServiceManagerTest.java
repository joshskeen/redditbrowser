package com.joshskeen.redditbrowser;

import android.content.Context;

import com.joshskeen.redditbrowser.model.Post;
import com.joshskeen.redditbrowser.service.RedditServiceManager;
import com.joshskeen.redditbrowser.service.oauth.RedditOauthAccessTokenService;
import com.joshskeen.redditbrowser.util.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import rx.observers.TestSubscriber;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class RedditServiceManagerTest {

    private Context mContext;
    private SharedPreferencesManager mSharedPreferencesManager;
    private ServiceDataManager mServiceDataManager;
    private RedditOauthAccessTokenService mOauthService;

    @Before
    public void setup() {
        mContext = Robolectric.getShadowApplication().getApplicationContext();
        mSharedPreferencesManager = new SharedPreferencesManager(mContext);
        mServiceDataManager = new ServiceDataManager(mContext, mSharedPreferencesManager);
        mOauthService = RedditOauthAccessTokenService.getInstance(mServiceDataManager, mContext, TestUtils.getRandomStringOfLength(25));
    }

    @Test
    public void redditServiceManagerReturnsListOfRandomPosts() {
        RedditServiceManager serviceManager = getServiceManager();
        TestSubscriber<List<Post>> testSubscriber = new TestSubscriber();
        serviceManager.getTopPosts().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertNoErrors();
        List<Post> posts = testSubscriber.getOnNextEvents().get(0);
        assertThat(posts.size()).isGreaterThan(0);
    }

    private RedditServiceManager getServiceManager() {
        return new RedditServiceManager(mOauthService, mServiceDataManager);
    }

}
