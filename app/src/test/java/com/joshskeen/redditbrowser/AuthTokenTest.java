package com.joshskeen.redditbrowser;

import android.content.Context;

import com.joshskeen.redditbrowser.model.response.AccessToken;
import com.joshskeen.redditbrowser.service.RedditServiceManager;
import com.joshskeen.redditbrowser.service.oauth.RedditOauthAccessTokenService;
import com.joshskeen.redditbrowser.util.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class AuthTokenTest {

    private RedditOauthAccessTokenService service;
    private Context mContext;
    private ServiceDataManager mServiceDataManager;
    private SharedPreferencesManager mSharedPreferencesManager;

    @Before
    public void setup() {
        mContext = Robolectric.getShadowApplication().getApplicationContext();
        mSharedPreferencesManager = new SharedPreferencesManager(mContext);
        mServiceDataManager = new ServiceDataManager(mContext, mSharedPreferencesManager);
    }

    @Test
    public void accessTokenRequestReturnsAccessToken() {
        TestSubscriber<AccessToken> accessTokenTestSubscriber = new TestSubscriber<AccessToken>();
        service = RedditOauthAccessTokenService.getInstance(mServiceDataManager, mContext, TestUtils.getRandomStringOfLength(25));
        Observable<AccessToken> accessTokenObservable = service.rxGetAccessToken();
        accessTokenObservable.subscribe(accessTokenTestSubscriber);
        accessTokenTestSubscriber.awaitTerminalEvent();
        accessTokenTestSubscriber.assertNoErrors();
        AccessToken accessToken = accessTokenTestSubscriber.getOnNextEvents().get(0);
        assertThat(accessToken.mAccessToken).isNotNull();
        assertThat(accessToken.mExpiresIn).isEqualTo(3600);
    }

    //the same in-memory version of the token is returned whehn available
    @Test
    public void getAccessTokenReturnsInMemoryCachedToken() {
        TestSubscriber<AccessToken> accessTokenTestSubscriber = new TestSubscriber<AccessToken>();
        TestSubscriber<AccessToken> accessTokenTestSubscriber2 = new TestSubscriber<AccessToken>();

        service = RedditOauthAccessTokenService.getInstance(mServiceDataManager, mContext, TestUtils.getRandomStringOfLength(25));
        RedditServiceManager manager = new RedditServiceManager(service, mServiceDataManager);

        Observable<AccessToken> accessTokenObservable = manager.getAccessTokenObservable();
        accessTokenObservable.subscribe(accessTokenTestSubscriber);
        accessTokenTestSubscriber.awaitTerminalEvent();
        AccessToken accessTokenA = accessTokenTestSubscriber.getOnNextEvents().get(0);

        Observable<AccessToken> accessTokenObservable2 = manager.getAccessTokenObservable();
        accessTokenObservable2.subscribe(accessTokenTestSubscriber2);
        accessTokenTestSubscriber2.awaitTerminalEvent();
        AccessToken accessTokenB = accessTokenTestSubscriber2.getOnNextEvents().get(0);
        assertThat(accessTokenA.mAccessToken).isEqualTo(accessTokenB.mAccessToken);
    }

    //a cached Datamanager version of the token is returned when available
    @Test
    public void getAccessTokenReturnsServiceDataManagerTokenWhenAvailable() {
        TestSubscriber<AccessToken> accessTokenTestSubscriber = new TestSubscriber<AccessToken>();
        TestSubscriber<AccessToken> accessTokenTestSubscriber2 = new TestSubscriber<AccessToken>();
        service = RedditOauthAccessTokenService.getInstance(mServiceDataManager, mContext, TestUtils.getRandomStringOfLength(25));
        AccessToken accessToken = new AccessToken();
        accessToken.mAccessToken = "foobar123";
        accessToken.mExpiresIn = 3600;
        mServiceDataManager.setAccessToken(accessToken);

        RedditServiceManager manager = new RedditServiceManager(service, mServiceDataManager);
        manager.getAccessTokenObservable();

        manager.getAccessTokenObservable().subscribe(accessTokenTestSubscriber);
        accessTokenTestSubscriber.awaitTerminalEvent();
        assertThat(accessTokenTestSubscriber.getOnNextEvents().get(0).mAccessToken).isEqualTo("foobar123");
        //emptying the datamanager's token and requesting a token should cause a new token to be requested
        manager.resetAuthToken();
        manager.getAccessTokenObservable().subscribe(accessTokenTestSubscriber2);
        accessTokenTestSubscriber2.awaitTerminalEvent();
        //the token was reset, a new one should be requested!
        assertThat(accessTokenTestSubscriber2.getOnNextEvents().get(0).mAccessToken).isNotEqualTo("foobar123");
    }

}