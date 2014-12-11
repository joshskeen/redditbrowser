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

import com.joshskeen.redditbrowser.model.Post;
import com.joshskeen.redditbrowser.service.RedditServiceManager;
import com.joshskeen.redditbrowser.view.RedditDataRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.functions.Action1;

public class RedditBrowserFragment extends BaseFragment {

    @InjectView(R.id.fragment_swipe_refresh_recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.fragment_swipe_refresh_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    RedditServiceManager mRedditServiceManager;

    @Inject
    ServiceDataManager mServiceDataManager;

    private RedditDataRecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.swipe_refresh_layout, container, false);
        ButterKnife.inject(this, inflate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RedditDataRecyclerViewAdapter(getActivity(), mServiceDataManager);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LAYOUT_DIRECTION_RTL);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRedditServiceManager.getTopPostsWithPicsOnly().subscribe(new Action1<List<Post>>() {
                    @Override
                    public void call(List<Post> posts) {
                        mServiceDataManager.setPosts(posts);
                        refreshContent();
                    }
                });
            }
        });
        return inflate;
    }


    private void refreshContent() {
        mAdapter = new RedditDataRecyclerViewAdapter(getActivity(), mServiceDataManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        ActionBarActivity activity = (ActionBarActivity) getActivity();
        activity.setTitle(mServiceDataManager.getSectionTitle());
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
