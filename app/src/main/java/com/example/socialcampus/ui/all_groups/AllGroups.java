package com.example.socialcampus.ui.all_groups;

import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.example.socialcampus.R;
import com.example.socialcampus.ui.home.GroupBoxAdapter;
import com.example.socialcampus.ui.home.GroupBoxCard;
import com.google.android.material.snackbar.Snackbar;
import java.util.LinkedList;
import java.util.Random;

public class AllGroups extends Fragment {

    private View view;
    private RecyclerView groupRecyclerViewAll;
    private RecyclerView groupRecyclerViewUteliv;
    private RecyclerView groupRecyclerViewSport;
    private GroupBoxAdapter groupAdapter;
    private final LinkedList<GroupBoxCard> alleGrupper = new LinkedList<>();
    private final LinkedList<GroupBoxCard> uteliv = new LinkedList<>();
    private final LinkedList<GroupBoxCard> sport = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.fragment_all_groups, container, false);

        initializeView();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initializeData();
    }

    private void initializeView() {
        recyclerViewInit();
    }

    private void recyclerViewInit() {
        groupRecyclerViewAll = view.findViewById(R.id.all_groups_recycler_all);
        groupAdapter = new GroupBoxAdapter(getContext(), alleGrupper);
        groupRecyclerViewAll.setAdapter(groupAdapter);
        groupRecyclerViewAll.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        groupRecyclerViewUteliv = view.findViewById(R.id.all_groups_recycler_uteliv);
        groupAdapter = new GroupBoxAdapter(getContext(), uteliv);
        groupRecyclerViewUteliv.setAdapter(groupAdapter);
        groupRecyclerViewUteliv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        groupRecyclerViewSport = view.findViewById(R.id.all_groups_recycler_sport);
        groupAdapter = new GroupBoxAdapter(getContext(), sport);
        groupRecyclerViewSport.setAdapter(groupAdapter);
        groupRecyclerViewSport.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    private void initializeData() {
        getData(alleGrupper);
        getData(uteliv);
        getData(sport);
        groupAdapter.notifyDataSetChanged();
        recyclerAnimation(groupRecyclerViewAll);
        recyclerAnimation(groupRecyclerViewUteliv);
        recyclerAnimation(groupRecyclerViewSport);
    }

    private void getData(LinkedList<GroupBoxCard> groupCard) {
        //https://stackoverflow.com/questions/29819204/could-android-store-drawable-ids-like-an-integer-array
        TypedArray tArray = getResources().obtainTypedArray(
                R.array.group_pictures);
        int count = tArray.length();
        int[] imgIdArr = new int[count];
        for (int i = 0; i < imgIdArr.length; i++) {
            imgIdArr[i] = tArray.getResourceId(i, 0);
        }
        tArray.recycle();

        int[] groupImg = imgIdArr;
        String[] groupTitle = getResources().getStringArray(R.array.group_title);
        String[] groupNumMembers = getResources().getStringArray(R.array.group_member_count);
        String[] groupNumPosts = getResources().getStringArray(R.array.group_post_count);

        setData(groupCard, groupImg, groupTitle, groupNumMembers, groupNumPosts);
    }

    private void setData(LinkedList<GroupBoxCard> groupCard, int[] groupImg, String[] groupTitle,
                        String[] groupNumMembers, String[] groupNumPosts) {
        //Random for å vise tilfeldige grupper pr rad
        Random ran = new Random();
        int x = ran.nextInt(5);
        int y = ran.nextInt(6) + 5;

        for (int i = x; i < y; i++) {
            groupCard.add(new GroupBoxCard(groupImg[i], groupTitle[i], groupNumMembers[i], groupNumPosts[i]));
        }
    }

    private void recyclerAnimation(final RecyclerView recyclerView) {
        recyclerView.getViewTreeObserver().addOnPreDrawListener(
            new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                    int tid = 500;
                    for (int i = 0; i < recyclerView.getChildCount(); i++) {
                        tid = tid + 100;
                        View v = recyclerView.getChildAt(i);
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
    }

    private void makeSnackbar(String melding) {
        final Snackbar snackBar = Snackbar.make(view, melding, Snackbar.LENGTH_LONG);
        snackBar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }
}
