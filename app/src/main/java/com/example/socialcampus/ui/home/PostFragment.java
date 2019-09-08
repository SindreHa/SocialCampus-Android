package com.example.socialcampus.ui.home;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialcampus.R;
import com.example.socialcampus.ui.group.GroupFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {



    public PostFragment() {
        // Required empty public constructor
    }


    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.post_fragment, container, false);
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
                Log.d(LOG_TAG, "postFragment");
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), groupFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });


    }

}
