package com.joshskeen.redditbrowser;

import com.joshskeen.redditbrowser.model.response.AccessToken;
import com.joshskeen.redditbrowser.service.oauth.RedditOauthAccessTokenService;
import com.joshskeen.redditbrowser.util.TestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import rx.Observable;
import rx.observers.TestSubscriber;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class RxJavaTest {

    private static final String TAG = "RxJavaTest";
    private RedditOauthAccessTokenService service;

    @Test
    public void testRxRequest() {
        TestSubscriber<AccessToken> accessTokenTestSubscriber = new TestSubscriber<AccessToken>();
        service = RedditOauthAccessTokenService.getInstance(
                Robolectric.getShadowApplication().getApplicationContext(),
                TestUtils.getRandomStringOfLength(25));
        Observable<AccessToken> accessTokenObservable = service.rxGetAccessToken();
        accessTokenObservable.subscribe(accessTokenTestSubscriber);
        accessTokenTestSubscriber.awaitTerminalEvent();
        accessTokenTestSubscriber.assertNoErrors();
    }

}
