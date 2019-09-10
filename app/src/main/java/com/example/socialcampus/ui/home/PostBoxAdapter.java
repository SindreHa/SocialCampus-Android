package com.example.socialcampus.ui.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostBoxAdapter extends RecyclerView.Adapter<PostBoxAdapter.PostBoxHolder>{

    public PostBoxAdapter(){}

    @NonNull
    @Override
    public PostBoxAdapter.PostBoxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostBoxAdapter.PostBoxHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PostBoxHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final PostBoxAdapter adapter;
        //final TextView title;
        //final TextView author;
        //final TextView description;

        public PostBoxHolder(View itemView, PostBoxAdapter adapter){
            super(itemView);
            this.adapter = adapter;
            //this.title = itemView.findViewById(R.id.)
        }

        @Override
        public void onClick(View view) {

        }
    }
}
