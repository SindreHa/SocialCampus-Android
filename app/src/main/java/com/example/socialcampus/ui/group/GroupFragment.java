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
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

    public final static String ENDPOINT = "https://itfag.usn.no/~kvisli/api.php";
    private View root;
    private ArrayList<PostCard> postCardList = new ArrayList<>();
    private RecyclerView postRecyclerView;
    private PostListAdapter postAdapter;
    private RestDbAdapterVolley restDb;
    private SwipeRefreshLayout refresh;
    Boolean refreshed = false;
    private PostCard postCard = null;
    String LOG_TAG = GroupFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_group, container, false);

        postRecyclerView = root.findViewById(R.id.group_post_recycler);
        postAdapter = new PostListAdapter(getContext(), postCardList);
        postRecyclerView.setAdapter(postAdapter);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        refresh = root.findViewById(R.id.group_refresh);
        refresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        postAnimationOut();
                    }
                }
        );

        FloatingActionButton newPost = root.findViewById(R.id.new_post_button);
        //https://developer.android.com/guide/navigation/navigation-getting-started#java
        newPost.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_groups_to_nav_new_post));

        initializeData(20);

        restDb = new RestDbAdapterVolley(getContext());

        ItemTouchHelper.SimpleCallback helper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

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

            //https://github.com/xabaras/RecyclerViewSwipeDecorator
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
        lesEnPost();
        refresh.setRefreshing(false);
        //Aktiver scrolling for recyclerview etter animasjon
        postRecyclerView.setLayoutFrozen(false);

        /*
        postCardList.add(new PostCard(getString(R.string.placeholder_title), getString(R.string.username), getString(R.string.placeholder_group_name), getString(R.string.placeholder_text),
                    getString(R.string.placeholder_comment_count), getString(R.string.placeholder_like_count), getString(R.string.placeholder_timestamp)));
        */

    }

    public void lesEnPost() {
        //String postListeURL = ENDPOINT + "/Post/" + postNr.trim();
        if (isOnline()){

            RequestQueue queue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest =
                    new StringRequest(Request.Method.GET, "https://itfag.usn.no/~146005/api.php/post?order=user_id,asc&transform=1", this, this);
            queue.add(stringRequest);
        }else{
            Toast.makeText(getContext(), "Nettverksfeil.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(String response) {
        try{
            Log.d(LOG_TAG, response);
            postCardList = PostCard.lagPostListe(response);
            oppdaterPostView(postCardList);
        }catch (JSONException e){

        }
        postAnimation();
        //Aktiver scrolling for recyclerview etter animasjon
        postRecyclerView.setLayoutFrozen(false);
        postAdapter.notifyDataSetChanged();
    }

    public void oppdaterPostView(ArrayList<PostCard> nyPostListe) {
        postAdapter = new PostListAdapter(getContext(), postCardList);
        postRecyclerView.setAdapter(postAdapter);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                                            initializeData(0);
                                        }
                                    }))
                                    .start();
                        }
                        return true;
                    }
                });
    }
}