package com.example.socialcampus.ui.about_us;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialcampus.R;

import java.util.ArrayList;

public class AboutUsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<AboutUscard> mAboutUsData;
    private AboutUsAdapter mAdapter;
    private View mView;

    String LOG_CAT = AboutUsFragment.class.getSimpleName();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_us, container, false);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.about_us_recycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAboutUsData = new ArrayList<>();
        mAdapter = new AboutUsAdapter(getContext(), mAboutUsData);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();

        this.mView = root;
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initializeData() {
        String[] names = getResources().getStringArray(R.array.about_us_names);
        TypedArray pictures = getResources().obtainTypedArray(R.array.about_us_pictures);

        int length = names.length;
        Log.d(LOG_CAT, "Init " + length);


        for (int i=0;i<names.length;i++) {
            mAboutUsData.add(new AboutUscard());
        }

        pictures.recycle();
        mAdapter.notifyDataSetChanged();
    }
}