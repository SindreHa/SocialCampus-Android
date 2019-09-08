package com.example.socialcampus.ui.home;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.socialcampus.R;
import com.example.socialcampus.ui.group.GroupFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }

    private void displayToast(String t) {
        Toast.makeText(getContext(), t, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final CardView card_view = getView().findViewById(R.id.cardView);
        final String LOG_TAG = HomeFragment.class.getSimpleName();
        final GroupFragment groupFragment = new GroupFragment();

        card_view.setOnClickListener(new View.OnClickListener() {

            //String getName = ((TextView)card_view.getChildAt(1)).getText().toString();
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Klikk på post");
                displayToast("Klikk på post");
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), groupFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}