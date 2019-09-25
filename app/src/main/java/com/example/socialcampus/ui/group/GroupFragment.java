package com.example.socialcampus.ui.group;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialcampus.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import java.util.ArrayList;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class GroupFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener{

    private View view;
    private RecyclerView postRecyclerView;
    private PostListAdapter postAdapter;
    private SwipeRefreshLayout refresh;
    private RestDbAdapterVolley restDb;
    private ArrayList<PostCard> postCardList = new ArrayList<>();
    private final String LOG_TAG = GroupFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.fragment_group, container, false);

        initializeView();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initializeData();
    }

    private void initializeView() {
        recyclerViewInit();
        tabViewInit();
        floatingButtonInit();
    }

    private void recyclerViewInit() {
        postRecyclerView = view.findViewById(R.id.group_post_recycler);
        postAdapter = new PostListAdapter(getContext(), postCardList);
        postRecyclerView.setAdapter(postAdapter);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerViewMethods();
    }

    private void recyclerViewMethods() {

        //Refresh listener
        refresh = view.findViewById(R.id.group_refresh);
        refresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent), Color.RED, Color.GREEN);
        refresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimary));
        refresh.setRefreshing(true);
        refresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        postAnimationOut();
                    }
                }
        );

        //Swipe listener
        ItemTouchHelper.SimpleCallback helper = new ItemTouchHelper.SimpleCallback(
                0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    PostCard post = postCardList.get(viewHolder.getAdapterPosition());
                    restDb.deletePost(post.getPostId());
                    postCardList.remove(viewHolder.getAdapterPosition());
                    postAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                    final Snackbar snackBar = Snackbar.make(getView(), "Innlegg slettet", Snackbar.LENGTH_LONG);
                    snackBar.setAction("Angre", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Her kan man legge inn angrefunksjon på sletting av innlegg
                            snackBar.dismiss();
                        }
                    });
                    snackBar.show();
                }
            }

            /*
            * Bibliotek fra github for å sette ikoner og tekst bak elementer i recyclerview som vises på swipe
            * https://github.com/xabaras/RecyclerViewSwipeDecorator
            */
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .setSwipeLeftActionIconTint(Color.RED)
                        .addSwipeLeftLabel("Slett innlegg")
                        .setSwipeLeftLabelColor(Color.WHITE)
                        .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryBackground))
                        .addSwipeRightActionIcon(R.drawable.ic_edit)
                        .addSwipeRightLabel("Rediger innlegg")
                        .setSwipeRightLabelColor(Color.WHITE)
                        .create()
                        .decorate();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(helper);
        itemTouchHelper.attachToRecyclerView(postRecyclerView);
    }

    private void tabViewInit() {
        TabLayout tabLayout = view.findViewById(R.id.tab_sort_group);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        initializeData();
                        break;
                    case 1:
                        initializeData();
                        break;
                    case 2:
                        initializeData();
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
    }

    private void floatingButtonInit() {
        FloatingActionButton newPost = view.findViewById(R.id.new_post_button);
        //https://developer.android.com/guide/navigation/navigation-getting-started#java
        newPost.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_groups_to_nav_new_post));
    }

    private void initializeData() {
        restDb= new RestDbAdapterVolley(getContext());
        postCardList.clear();
        getPostData();
        //Aktiver scrolling for recyclerview etter animasjon
        postRecyclerView.setLayoutFrozen(false);
    }

    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void getPostData() {
        //String postListeURL = ENDPOINT + "/Post/" + postNr.trim();
        if (isOnline()){
            RequestQueue queue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest =
                    new StringRequest(Request.Method.GET, "https://itfag.usn.no/~146005/api.php/post?order=id,desc&transform=1", this, this);
            queue.add(stringRequest);
        }else{
            makeSnackbar("Nettverksfeil");
        }
    }

    @Override
    public void onResponse(String response) {
        try{
            Log.d(LOG_TAG, response);
            postCardList = PostCard.lagPostListe(response);
            updatePostRecycler();
            //Stopper refresh ikonet
            refresh.setRefreshing(false);
        }catch (JSONException e){

        }
        postAnimation();
        //Aktiver scrolling for recyclerview etter animasjon
        postRecyclerView.setLayoutFrozen(false);
        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), error.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    public void updatePostRecycler() {
        postAdapter = new PostListAdapter(getContext(), postCardList);
        postRecyclerView.setAdapter(postAdapter);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void postAnimation(){
        //Deaktiver scrolling for å unngå krasj som skjer hvor man scroller samtidig som animasjon
        postRecyclerView.setLayoutFrozen(true);
        postRecyclerView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        postRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                        int duration = 450;
                        for (int i = 0; i < postRecyclerView.getChildCount(); i++) {
                            duration = duration + 100;
                            View v = postRecyclerView.getChildAt(i);
                            View pRecycler = postRecyclerView;
                            v.setAlpha(1.0f);
                            v.setTranslationY(pRecycler.getHeight());
                            v.animate()
                                    .translationY(0)
                                    .setInterpolator(new AccelerateDecelerateInterpolator())
                                    .alpha(1.0f)
                                    .setDuration(duration)
                                    .setStartDelay(i * 50)
                                    .start();
                        }
                        return true;
                    }
                });
    }

    private void postAnimationOut(){
        //Deaktiver scrolling for å unngå krasj som skjer hvor man scroller samtidig som animasjon
        postRecyclerView.setLayoutFrozen(true);
        postRecyclerView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        postRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                        int duration = 450;
                        for (int i = 0; i < postRecyclerView.getChildCount(); i++) {
                            duration = duration + 100;
                            View v = postRecyclerView.getChildAt(i);
                            View pRecycler = postRecyclerView;
                            v.setTranslationX(0);
                            v.animate()
                                    .translationX(pRecycler.getWidth())
                                    .setInterpolator(new AccelerateDecelerateInterpolator())
                                    .alpha(0f)
                                    .setDuration(duration)
                                    .setStartDelay(i * 50)
                                    .withEndAction(new Thread(new Runnable() {
                                        public void run() {
                                            initializeData();
                                        }
                                    }))
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