package com.joshskeen.redditbrowser.service.oauth;

import com.joshskeen.redditbrowser.model.response.AccessToken;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

interface RedditOauthAccessTokenAPI {
    @FormUrlEncoded
    @POST("/access_token")
    //grant_type
    public AccessToken getInstalledClientGrant(@Field("grant_type") String grant, @Field("device_id") String deviceId, @Field("duration") String duration);

    @FormUrlEncoded
    @POST("/access_token")
    //grant_type
    public void getAsyncInstalledClientGrant(@Field("grant_type") String grant, @Field("device_id") String deviceId, Callback<AccessToken> callback);

    @FormUrlEncoded
    @POST("/access_token")
    //grant_type
    public Observable<AccessToken> rxGetAsyncInstalledClientGrant(@Field("grant_type") String grant, @Field("device_id") String deviceId);
}
