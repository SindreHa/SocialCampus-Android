package com.example.socialcampus.ui.about_us;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialcampus.R;

import java.util.ArrayList;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.ViewHolder> {

    private ArrayList<AboutUscard> mAboutUsData;
    private Context mContext;
    private ImageView mProfilePhoto;

    AboutUsAdapter(Context context, ArrayList<AboutUscard> AboutUsData) {
        this.mAboutUsData = AboutUsData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public AboutUsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.about_us_card, parent, false));
    }

    @Override
    public void onBindViewHolder(AboutUsAdapter.ViewHolder holder, int position) {
        AboutUscard currentPerson = mAboutUsData.get(position);
        holder.mNavn.setText("heu");
        holder.mBilde.setImageResource(R.drawable.fotball);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNavn;
        private ImageView mBilde;

        ViewHolder(View itemView) {
            super(itemView);

            mNavn = itemView.findViewById(R.id.about_us_name);
            mBilde = itemView.findViewById(R.id.about_us_picture);
        }

        void bindTo(int position) {
            //mNavn.setText(AboutUscard.aNavn[position]);
            //mProfilePhoto.setImageResource(AboutUscard.aBilder[position]);
           // Glide.with(mContext).load(currentPerson.getBilde()).into(mProfilePhoto);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
