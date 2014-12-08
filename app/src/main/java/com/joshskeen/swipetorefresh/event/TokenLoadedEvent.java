package com.joshskeen.swipetorefresh.event;

import com.joshskeen.swipetorefresh.model.response.AccessToken;

public class TokenLoadedEvent {
    private AccessToken mAccessToken;

    public TokenLoadedEvent(AccessToken accessToken) {
        mAccessToken = accessToken;
    }
}
