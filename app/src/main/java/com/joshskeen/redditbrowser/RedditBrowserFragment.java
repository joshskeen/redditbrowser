package com.joshskeen.redditbrowser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joshskeen.redditbrowser.event.PostsLoadedEvent;
import com.joshskeen.redditbrowser.event.RetrofitErrorEvent;
import com.joshskeen.redditbrowser.event.TokenLoadedEvent;
import com.joshskeen.redditbrowser.service.RedditServiceManager;
import com.joshskeen.redditbrowser.view.RedditDataRecyclerViewAdapter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

public class RedditBrowserFragment extends BaseFragment {

    @InjectView(R.id.fragment_swipe_refresh_recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.fragment_swipe_refresh_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    ServiceDataManager mDataManager;
    @Inject
    RedditServiceManager mRedditServiceManager;

    private RedditDataRecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
//            mRedditServiceManager.loadTopPosts();
        } else {
            refreshContent();
        }
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.swipe_refresh_layout, container, false);
        ButterKnife.inject(this, inflate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RedditDataRecyclerViewAdapter(getActivity(), mDataManager);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LAYOUT_DIRECTION_RTL);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mRedditServiceManager.loadTopPosts();
            }
        });
        return inflate;
    }

    public void onEvent(PostsLoadedEvent event) {
        refreshContent();
    }

    public void onEvent(TokenLoadedEvent event) {
        refreshContent();
    }

    public void onEvent(RetrofitErrorEvent event) {
        //try refreshing the token
//        mRedditOauthAccessTokenServiceManager.loadAccessToken();
    }

    private void refreshContent() {
        mAdapter = new RedditDataRecyclerViewAdapter(getActivity(), mDataManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        ActionBarActivity activity = (ActionBarActivity) getActivity();
        activity.setTitle(mDataManager.getSectionTitle());
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
