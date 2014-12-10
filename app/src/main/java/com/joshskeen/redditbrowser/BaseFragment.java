package com.joshskeen.redditbrowser;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.joshskeen.redditbrowser.inject.RedditBrowserApplication;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RedditBrowserApplication.get(getActivity()).inject(this);
    }
}
