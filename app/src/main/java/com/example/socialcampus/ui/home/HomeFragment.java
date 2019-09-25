package com.example.socialcampus.ui.home;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialcampus.R;
import com.example.socialcampus.ui.group.PostCard;
import com.example.socialcampus.ui.group.PostListAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.LinkedList;

public class HomeFragment extends Fragment {

    private final LinkedList<GroupBoxCard> gCardList = new LinkedList<>();
    private RecyclerView gRecyclerView;
    private GroupBoxAdapter gAdapter;
    private final ArrayList<PostCard> postCardList = new ArrayList<>();
    private RecyclerView postRecyclerView;
    private PostListAdapter postAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        TabLayout tabLayout = root.findViewById(R.id.tab_sort_home);

        gRecyclerView = root.findViewById(R.id.recyclerView_group);
        gAdapter = new GroupBoxAdapter(getContext(), gCardList);
        gRecyclerView.setAdapter(gAdapter);

        //https://stackoverflow.com/questions/38909542/how-to-animate-recyclerview-items-when-adapter-is-initialized-in-order
        gRecyclerView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        gRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                        for (int i = 0; i < gRecyclerView.getChildCount(); i++) {
                            View v = gRecyclerView.getChildAt(i);
                            v.setAlpha(0.0f);
                            v.setTranslationX(400);
                            v.animate()
                                    .translationX(0)
                                    .setInterpolator(new AccelerateDecelerateInterpolator())
                                    .alpha(1.0f)
                                    .setDuration(550)
                                    .setStartDelay(i * 100)
                                    .start();
                        }
                        return true;
                    }
                });

        //https://stackoverflow.com/questions/2795833/check-orientation-on-android-phone
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        } else {
            gRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        }

        postRecyclerView = root.findViewById(R.id.group_post_recycler_home);
        postAdapter = new PostListAdapter(getContext(), postCardList);
        postRecyclerView.setAdapter(postAdapter);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //https://stackoverflow.com/questions/38909542/how-to-animate-recyclerview-items-when-adapter-is-initialized-in-order


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

        initializeData(10);

        return root;
    }

    private void initializeData(int antallPoster) {

        //https://stackoverflow.com/questions/29819204/could-android-store-drawable-ids-like-an-integer-array
        TypedArray tArray = getResources().obtainTypedArray(
                R.array.group_pictures);
        int count = tArray.length();
        int[] ids = new int[count];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = tArray.getResourceId(i, 0);
        }
        tArray.recycle();

        int[] gruppeBilder = ids;
        String[] gruppeTittel = getResources().getStringArray(R.array.group_title);
        String[] gruppeAntMeldemmer = getResources().getStringArray(R.array.group_member_count);
        String[] gruppeAntPoster = getResources().getStringArray(R.array.group_post_count);

        gCardList.clear();
        for (int i = 0; i < 10; i++){
            gCardList.addLast(new GroupBoxCard(gruppeBilder[i], gruppeTittel[i], gruppeAntMeldemmer[i], gruppeAntPoster[i]));
        }

        postCardList.clear();
        for (int i=0; i<antallPoster; i++) {
            postCardList.add(new PostCard(getString(R.string.placeholder_title), getString(R.string.username), getString(R.string.placeholder_group_name), getString(R.string.placeholder_text),
                    getString(R.string.placeholder_comment_count), getString(R.string.placeholder_like_count)));
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

        gAdapter.notifyDataSetChanged();
        postAdapter.notifyDataSetChanged();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    private void makeSnackbar(String melding) {
        final Snackbar snackBar = Snackbar.make(getView(), melding, Snackbar.LENGTH_LONG);
        snackBar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }
}