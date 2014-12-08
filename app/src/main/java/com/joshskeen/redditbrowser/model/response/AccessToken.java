package com.joshskeen.redditbrowser.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccessToken implements Serializable {
    @SerializedName("access_token")
    public String mAccessToken;
    @SerializedName("token_type")
    public String mTokenType;
    @SerializedName("expires_in")
    public int mExpiresIn;
    @SerializedName("scope")
    public String mScope;

    @Override
    public String toString() {
        return "AccessTokenResponse{" +
                "mAccessToken='" + mAccessToken + '\'' +
                ", mTokenType='" + mTokenType + '\'' +
                ", mExpiresIn=" + mExpiresIn +
                ", mScope='" + mScope + '\'' +
                '}';
    }
}
