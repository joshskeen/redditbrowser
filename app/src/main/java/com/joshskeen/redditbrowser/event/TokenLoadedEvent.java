package com.joshskeen.redditbrowser.event;

import com.joshskeen.redditbrowser.model.response.AccessToken;

public class TokenLoadedEvent {
    private AccessToken mAccessToken;

    public TokenLoadedEvent(AccessToken accessToken) {
        mAccessToken = accessToken;
    }
}
