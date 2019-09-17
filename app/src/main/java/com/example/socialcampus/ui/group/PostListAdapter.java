package com.example.socialcampus.ui.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialcampus.R;

import java.util.LinkedList;

public class PostListAdapter extends RecyclerView.Adapter<com.example.socialcampus.ui.group.PostListAdapter.PostCardHolder>{

    private final LinkedList<PostCard> postCardList;
    private LayoutInflater inflater;

    public PostListAdapter(Context context, LinkedList<PostCard> postCardList){
        inflater = LayoutInflater.from(context);
        this.postCardList = postCardList;
    }

    @NonNull
    @Override
    public com.example.socialcampus.ui.group.PostListAdapter.PostCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.post_card, parent, false);
        return new com.example.socialcampus.ui.group.PostListAdapter.PostCardHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.socialcampus.ui.group.PostListAdapter.PostCardHolder holder, int position) {
        //holder.sportImg.setImageResource(postCardList.get(position).getImagePath());
        holder.postTitle.setText(postCardList.get(position).getPostTitle());
        holder.postAuthor.setText(postCardList.get(position).getPostAuthor());
        holder.postGroupName.setText(postCardList.get(position).getPostGroupName());
        holder.postDescription.setText(postCardList.get(position).getPostDescription());
        holder.postCommentCount.setText(postCardList.get(position).getPostCommentCount());
        holder.postLikeCount.setText(postCardList.get(position).getPostLikeCount());
        holder.postTimestamp.setText(postCardList.get(position).getPostTimestamp());
    }

    @Override
    public int getItemCount() {
        return postCardList.size();
    }

    class PostCardHolder extends RecyclerView.ViewHolder {
        //public final ImageView sportImg;
        public final TextView postTitle;
        public final TextView postAuthor;
        public final TextView postGroupName;
        public final TextView postDescription;
        public final TextView postCommentCount;
        public final TextView postLikeCount;
        public final TextView postTimestamp;
        final com.example.socialcampus.ui.group.PostListAdapter adapter;

        public PostCardHolder(View itemView, com.example.socialcampus.ui.group.PostListAdapter adapter){
            super(itemView);
            //this.postTitle = itemView.findViewById(R.id.card_post_title);
            this.postTitle = itemView.findViewById(R.id.card_post_title);
            this.postAuthor = itemView.findViewById(R.id.card_post_author);
            this.postGroupName = itemView.findViewById(R.id.card_post_group_name);
            this.postDescription = itemView.findViewById(R.id.card_post_description);
            this.postCommentCount = itemView.findViewById(R.id.card_post_comment_stat);
            this.postLikeCount = itemView.findViewById(R.id.card_post_likes_stat);
            this.postTimestamp = itemView.findViewById(R.id.card_post_timestamp);
            this.adapter = adapter;
            //itemView.setOnClickListener(Snackbar.make(itemView, "Dette skal føre til kommentarer", Snackbar.LENGTH_LONG).show());
        }
    }
}
