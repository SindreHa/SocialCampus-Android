package com.example.socialcampus.ui.group;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialcampus.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;

public class GroupFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener{


    public final static String ENDPOINT = "https://itfag.usn.no/~kvisli/api.php";

    private View root;

    private final LinkedList<PostCard> postCardList = new LinkedList<>();
    private RecyclerView postRecyclerView;
    private PostListAdapter postAdapter;
    private RestDbAdapterVolley restDbAdapter;
    private PostCard postCard = null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_group, container, false);

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
        // Hente inn fra database her
        postCardList.clear();
        lesEnPost();
    }

    public void lesEnPost() {
        // postListeUrl må styres mot database
        //String postListeURL = ENDPOINT + "/Post/" + postNr.trim();
        if (isOnline()){

            RequestQueue queue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest =
                    new StringRequest(Request.Method.GET, "https://itfag.usn.no/~kvisli/api.php/Vare/33044", this, this);
            queue.add(stringRequest);
        }else{
            Toast.makeText(getContext(), "Nettverksfeil.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(getContext(), response,
                Toast.LENGTH_LONG).show();
        for (int i=0; i<10; i++) {
            postCardList.add(new PostCard(getString(R.string.placeholder_title), getString(R.string.username), getString(R.string.placeholder_group_name), getString(R.string.placeholder_text),
                    getString(R.string.placeholder_comment_count), getString(R.string.placeholder_like_count), getString(R.string.placeholder_timestamp)));
        }
        postAnimation();
        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), error.getMessage(),
                Toast.LENGTH_SHORT).show();
    }



    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void postAnimation(){
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
    }
}