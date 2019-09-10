package com.example.socialcampus.ui.LogIn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.socialcampus.R;

public class LogInFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_log_in, container, false);

        TextView button = root.findViewById(R.id.createUser);

        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_log_in_to_nav_sign_up));

        return root;
    }
}