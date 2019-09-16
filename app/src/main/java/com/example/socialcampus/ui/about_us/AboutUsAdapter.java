package com.example.socialcampus.ui.about_us;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialcampus.R;
import java.util.LinkedList;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.AboutUsHolder>{

    private final LinkedList<AboutUscard> aboutUsList;
    private LayoutInflater inflater;

    public AboutUsAdapter(Context context, LinkedList<AboutUscard> aboutUsList){
        inflater = LayoutInflater.from(context);
        this.aboutUsList = aboutUsList;
    }

    @NonNull
    @Override
    public AboutUsAdapter.AboutUsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.about_us_card, parent, false);
        return new AboutUsHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutUsAdapter.AboutUsHolder holder, int position) {
        holder.aboutUsImg.setImageResource(aboutUsList.get(position).getBilde());
        holder.aboutUsTitle.setText(aboutUsList.get(position).getAboutUsTitle());
        holder.aboutUsRole.setText(aboutUsList.get(position).getAboutUsRole());
        holder.aboutUsDescription.setText(aboutUsList.get(position).getAboutUsDescription());
    }

    @Override
    public int getItemCount() {
        return aboutUsList.size();
    }

    class AboutUsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView aboutUsImg;
        public final TextView aboutUsTitle;
        public final TextView aboutUsRole;
        public final TextView aboutUsDescription;
        final AboutUsAdapter adapter;

        public AboutUsHolder(View itemView, AboutUsAdapter adapter){
            super(itemView);
            this.aboutUsImg = itemView.findViewById(R.id.about_us_picture);
            this.aboutUsTitle = itemView.findViewById(R.id.about_us_title);
            this.aboutUsRole = itemView.findViewById(R.id.about_us_role);
            this.aboutUsDescription = itemView.findViewById(R.id.about_us_description);
            this.adapter = adapter;
            //itemView.setOnClickListener(itemView.findViewById(R.id.about_us_description).setVisibility(View.VISIBLE));
        }

        @Override
        public void onClick(View view) {
        }
    }
}
