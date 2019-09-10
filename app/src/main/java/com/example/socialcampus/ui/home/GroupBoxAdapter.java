package com.example.socialcampus.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialcampus.R;

import java.util.LinkedList;

public class GroupBoxAdapter extends RecyclerView.Adapter<GroupBoxAdapter.GroupBoxHolder>{

    private final LinkedList<GroupBoxCard> boxCardList;
    private LayoutInflater inflater;

    public GroupBoxAdapter(Context context, LinkedList<GroupBoxCard> boxCardList){
        inflater = LayoutInflater.from(context);
        this.boxCardList = boxCardList;
    }

    @NonNull
    @Override
    public GroupBoxAdapter.GroupBoxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.group_box_item, parent, false);
        return new GroupBoxHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupBoxAdapter.GroupBoxHolder holder, int position) {
        holder.img.setImageResource(boxCardList.get(position).getImagePath());
        holder.description.setText(boxCardList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return boxCardList.size();
    }

    class GroupBoxHolder extends RecyclerView.ViewHolder{
        public final ImageView img;
        public final TextView description;
        final GroupBoxAdapter adapter;

        public GroupBoxHolder(View itemView, GroupBoxAdapter adapter){
        super(itemView);
        this.img = itemView.findViewById(R.id.group_imgView);
        this.description = itemView.findViewById(R.id.group_textView);
        this.adapter = adapter;
        }
    }
}
