package com.example.socialcampus.ui.group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.socialcampus.R;

public class GroupFragment extends Fragment {

    private GroupViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        slideshowViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);

        View root = inflater.inflate(R.layout.fragment_comment, container, false);

        return root;
    }

}