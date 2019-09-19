package com.example.socialcampus.ui.about_us;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialcampus.R;
import java.util.LinkedList;

public class AboutUsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private final LinkedList<AboutUscard> mAboutUsData = new LinkedList<>();
    private AboutUsAdapter mAdapter;
    private View mView;

    String LOG_CAT = AboutUsFragment.class.getSimpleName();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_us, container, false);

        mRecyclerView = root.findViewById(R.id.about_us_recycler);
        mAdapter = new AboutUsAdapter(getContext(), mAboutUsData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        mRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                        int tid = 500;
                        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
                            tid = tid + 100;
                            View v = mRecyclerView.getChildAt(i);
                            v.setAlpha(1.0f);
                            v.setScaleX(0);
                            v.setScaleY(0);
                            v.animate()
                                    .setInterpolator(new OvershootInterpolator())
                                    .scaleX(1)
                                    .scaleY(1)
                                    .alpha(1.0f)
                                    .setDuration(tid)
                                    .setStartDelay(i * 50)
                                    .start();
                        }
                        return true;
                    }
                });

        initializeData();

        this.mView = root;
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initializeData() {

        //https://stackoverflow.com/questions/29819204/could-android-store-drawable-ids-like-an-integer-array
        TypedArray tArray = getResources().obtainTypedArray(
                R.array.about_us_pictures);
        int count = tArray.length();
        int[] ids = new int[count];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = tArray.getResourceId(i, 0);
        }
        tArray.recycle();

        int[] aboutUsPicture = ids;
        String[] aboutUsTitle = getResources().getStringArray(R.array.about_us_names);
        String[] aboutUsRole = getResources().getStringArray(R.array.about_us_roles);
        String aboutUsDescription = getResources().getString(R.string.placeholder_text);

        for (int i = 0; i < 3; i++){
            mAboutUsData.addLast(new AboutUscard(aboutUsPicture[i], aboutUsTitle[i], aboutUsRole[0], aboutUsDescription));
        }
    }
}