package com.example.socialcampus.ui.all_groups;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialcampus.R;
import com.example.socialcampus.ui.group.PostCard;
import com.example.socialcampus.ui.group.PostListAdapter;
import com.example.socialcampus.ui.home.GroupBoxAdapter;
import com.example.socialcampus.ui.home.GroupBoxCard;
import com.example.socialcampus.ui.home.HomeFragment;

import java.util.LinkedList;

public class AllGroups extends Fragment {

    private final LinkedList<GroupBoxCard> groupCardList = new LinkedList<>();
    private RecyclerView groupRecyclerView;
    private GroupBoxAdapter groupAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_groups, container, false);

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

        for (int i = 0; i < 10; i++) {
            groupCardList.add(new GroupBoxCard(gruppeBilder[i], gruppeTittel[i], gruppeAntMeldemmer[i], gruppeAntPoster[i]));
        }
        groupRecyclerView = root.findViewById(R.id.all_groups_recycler_all);
        groupAdapter = new GroupBoxAdapter(getContext(), groupCardList);
        groupRecyclerView.setAdapter(groupAdapter);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        groupRecyclerView = root.findViewById(R.id.all_groups_recycler_uteliv);
        groupAdapter = new GroupBoxAdapter(getContext(), groupCardList);
        groupRecyclerView.setAdapter(groupAdapter);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        groupRecyclerView = root.findViewById(R.id.all_groups_recycler_sport);
        groupAdapter = new GroupBoxAdapter(getContext(), groupCardList);
        groupRecyclerView.setAdapter(groupAdapter);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        return root;
    }

}
