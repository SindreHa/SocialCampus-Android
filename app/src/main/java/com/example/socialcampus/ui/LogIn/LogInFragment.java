package com.example.socialcampus.ui.LogIn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.socialcampus.R;

public class LogInFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        TextView button = view.findViewById(R.id.btnLoginRegister);
        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_log_in_to_nav_sign_up));

        return view;
    }
}