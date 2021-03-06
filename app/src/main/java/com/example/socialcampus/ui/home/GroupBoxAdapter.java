package com.example.socialcampus.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialcampus.R;

import java.util.LinkedList;

public class GroupBoxAdapter extends RecyclerView.Adapter<GroupBoxAdapter.GroupBoxHolder>{

    private final LinkedList<GroupBoxCard> boxCardList;
    private LayoutInflater inflater;
    private int lastPosition = -1;
    private Context context;

    class GroupBoxHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView sportImg;
        public final TextView sportTitle;
        public final TextView numMembers;
        public final TextView numPosts;
        CardView container;
        final GroupBoxAdapter adapter;

        public GroupBoxHolder(View itemView, GroupBoxAdapter adapter){
            super(itemView);
            container = itemView.findViewById(R.id.groupCard);
            this.sportImg = itemView.findViewById(R.id.group_card_img);
            this.sportTitle = itemView.findViewById(R.id.group_card_title);
            this.numMembers = itemView.findViewById(R.id.group_card_num_members);
            this.numPosts = itemView.findViewById(R.id.group_card_num_posts);
            this.adapter = adapter;
            //https://developer.android.com/guide/navigation/navigation-getting-started#java
            itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_groups));
        }

        @Override
        public void onClick(View view) {
        }
    }

    public GroupBoxAdapter(Context context, LinkedList<GroupBoxCard> boxCardList){
        inflater = LayoutInflater.from(context);
        this.boxCardList = boxCardList;
        this.context = context;
    }

    @NonNull
    @Override
    public GroupBoxAdapter.GroupBoxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.group_card_home, parent, false);
        return new GroupBoxHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupBoxAdapter.GroupBoxHolder holder, int position) {
        holder.sportImg.setImageResource(boxCardList.get(position).getImagePath());
        holder.sportTitle.setText(boxCardList.get(position).getDescription());
        holder.numMembers.setText(boxCardList.get(position).getCountMembers());
        holder.numPosts.setText(boxCardList.get(position).getCountPosts());
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.nav_default_enter_anim);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return boxCardList.size();
    }

}
