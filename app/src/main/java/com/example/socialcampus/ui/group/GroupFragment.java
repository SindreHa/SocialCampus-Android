package com.example.socialcampus.ui.group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialcampus.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;

public class GroupFragment extends Fragment {

    private final LinkedList<PostCard> postCardList = new LinkedList<>();
    private RecyclerView postRecyclerView;
    private PostListAdapter postAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group, container, false);

        postRecyclerView = root.findViewById(R.id.group_post_recycler);
        postAdapter = new PostListAdapter(getContext(), postCardList);
        postRecyclerView.setAdapter(postAdapter);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        FloatingActionButton newPost = root.findViewById(R.id.new_post_button);
        //https://developer.android.com/guide/navigation/navigation-getting-started#java
        newPost.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_groups_to_nav_new_post));

        initializeData(20);

        TabLayout tabLayout = root.findViewById(R.id.tab_sort_group);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        initializeData(10);
                        break;
                    case 1:
                        initializeData(2);
                        break;
                    case 2:
                        initializeData(3);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    private void initializeData(int antallPoster) {

        postCardList.clear();
        for (int i=0; i<antallPoster; i++) {
            postCardList.add(new PostCard(getString(R.string.placeholder_title), getString(R.string.username), getString(R.string.placeholder_group_name), getString(R.string.placeholder_text),
                    getString(R.string.placeholder_comment_count), getString(R.string.placeholder_like_count), getString(R.string.placeholder_timestamp)));
        }

        postRecyclerView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        postRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                        int tid = 450;
                        for (int i = 0; i < postRecyclerView.getChildCount(); i++) {
                            tid = tid + 100;
                            View v = postRecyclerView.getChildAt(i);
                            View reCycler = postRecyclerView;
                            v.setAlpha(1.0f);
                            v.setTranslationY(reCycler.getHeight());
                            v.animate()
                                    .translationY(0)
                                    .setInterpolator(new AccelerateDecelerateInterpolator())
                                    .alpha(1.0f)
                                    .setDuration(tid)
                                    .setStartDelay(i * 50)
                                    .start();
                        }
                        return true;
                    }
                });

        postAdapter.notifyDataSetChanged();
    }

}