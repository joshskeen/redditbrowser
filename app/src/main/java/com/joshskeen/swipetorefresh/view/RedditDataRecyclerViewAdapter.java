package com.joshskeen.swipetorefresh.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joshskeen.swipetorefresh.R;
import com.joshskeen.swipetorefresh.ServiceDataManager;
import com.joshskeen.swipetorefresh.model.Post;
import com.joshskeen.swipetorefresh.model.PostData;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RedditDataRecyclerViewAdapter extends RecyclerView.Adapter<RedditDataRecyclerViewAdapter.PostListViewHolder> {

    private List<Post> mPosts;
    private Context mContext;

    public RedditDataRecyclerViewAdapter(Context context, ServiceDataManager dataManager) {
        mContext = context;
        mPosts = dataManager.getPosts();
    }

    public Post getItem(int position) {
        return mPosts.get(position);
    }

    class PostListViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.layout_post_title)
        TextView mPostTitle;
        @InjectView(R.id.layout_post_description)
        TextView mPostDescription;
        @InjectView(R.id.layout_post_image)
        ImageView mPostImageView;

        public PostListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            System.out.println(mPostDescription);
        }
    }

    @Override
    public PostListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reddit_posts_list_item, parent, false);
        PostListViewHolder vh = new PostListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PostListViewHolder holder, int position) {
        Post item = getItem(position);
        PostData postData = item.mPostData;
        holder.mPostTitle.setText(postData.mTitle);
        if (postData.mSelfText != null) {
            holder.mPostDescription.setText((Html.fromHtml(postData.mSelfText)));
        }
        item.displayImage(mContext, holder.mPostImageView);
    }

    @Override
    public int getItemCount() {
        if (mPosts == null) {
            return 0;
        }
        return mPosts.size();
    }
}
