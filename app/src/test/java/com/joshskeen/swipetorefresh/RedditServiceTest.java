package com.joshskeen.swipetorefresh;

import android.content.Context;

import com.joshskeen.swipetorefresh.model.PostsResponse;
import com.joshskeen.swipetorefresh.model.response.AccessToken;
import com.joshskeen.swipetorefresh.service.RedditService;
import com.joshskeen.swipetorefresh.service.oauth.RedditOauthAccessTokenService;
import com.joshskeen.swipetorefresh.util.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "./src/main/AndroidManifest.xml", emulateSdk = 18)
public class RedditServiceTest {

    private RedditOauthAccessTokenService mRedditOauthAccessTokenService;

    @Before
    public void setup() {
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        String deviceId = TestUtils.getRandomStringOfLength(25);
        mRedditOauthAccessTokenService = RedditOauthAccessTokenService.getInstance(context, deviceId);
    }

    @Test
    public void assertTokenGrantsAccess(){
        AccessToken accessToken = mRedditOauthAccessTokenService.getAccessToken();
        RedditService instance = RedditService.getInstance(accessToken);
        PostsResponse allPosts = instance.getAllPosts();
        System.out.println(allPosts);
    }

    @Test public void urlIsImage(){
        String s = "http://www.google.com/foo.jpg";
        String regex = "http(s?)://([\\w-]+\\.)+[\\w-]+(/[\\w./]*)+\\.(?:[gG][iI][fF]|[jJ][pP][gG]|[jJ][pP][eE][gG]|[pP][nN][gG]|[bB][mM][pP])";
        Matcher m = Pattern.compile(regex).matcher(s);
        assertThat(m.find()).isTrue();

        String s2  = "http://www.google.com/foo";
        m = Pattern.compile(regex).matcher(s2);
        assertThat(m.find()).isFalse();

    }

}
