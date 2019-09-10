package com.example.socialcampus.ui.home;

import android.os.Bundle;
import android.os.Debug;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialcampus.R;
import com.example.socialcampus.ui.group.GroupFragment;

import java.util.LinkedList;

public class HomeFragment extends Fragment {

    public HomeViewModel homeViewModel;

    private final LinkedList<GroupBoxCard> gCardList = new LinkedList<>();
    private RecyclerView gRecyclerView;
    private GroupBoxAdapter gAdapter;

    // Hardkode for å vise recycleview (Sindre improve this ofc?)
    private int[] imgList = new int[]{R.drawable.jan, R.drawable.sindre, R.drawable.kristian,
                                    R.drawable.jan, R.drawable.sindre, R.drawable.kristian,
                                    R.drawable.jan, R.drawable.sindre, R.drawable.kristian,
                                    R.drawable.jan, R.drawable.sindre, R.drawable.kristian,
                                    R.drawable.jan, R.drawable.sindre, R.drawable.kristian};
    private String[] description = new String[]{"Tennis", "Fotball", "Bordtennis", "Gaming", "Friluft",
                                            "Tennis", "Fotball", "Bordtennis", "Gaming", "Friluft",
                                            "Tennis", "Fotball", "Bordtennis", "Gaming", "Friluft"};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        for (int i = 0; i < 10; i++){
            gCardList.addLast(new GroupBoxCard(imgList[i], description[i]));
        }

        gRecyclerView = root.findViewById(R.id.recyclerView_group);
        gAdapter = new GroupBoxAdapter(getContext(), gCardList, this);
        gRecyclerView.setAdapter(gAdapter);
        gRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return root;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }


}