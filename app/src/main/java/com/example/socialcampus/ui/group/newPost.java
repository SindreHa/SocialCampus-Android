package com.example.socialcampus.ui.group;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialcampus.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * Created by Sindre Haavaldsen on 17.09.19.
 */
public class newPost extends Fragment implements AdapterView.OnItemSelectedListener {

    private CardView sendPostButton;
    private EditText title;
    private EditText content;
    private RestDbAdapterVolley db;
    private View view;

    public newPost() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.new_post, container, false);
        this.view = root;

        db = new RestDbAdapterVolley(getContext());

        sendPostButton = root.findViewById(R.id.send_post_button);
        title = root.findViewById(R.id.new_post_title);
        content = root.findViewById(R.id.new_post_content);

        //Spinner for valg av gruppe
        Spinner spinner = root.findViewById(R.id.group_chooser);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.group_title, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        //Funksjon for trykk på publiseringsknapp
        sendPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTitle = title.getText().toString();
                String mContent = content.getText().toString();
                PostCard postCard = new PostCard(mTitle, "Brukernavn", "Tennis", mContent, "344", "1273", "19:53" );
                db.insertPostCard(postCard);
                hideKeyboardFrom(getContext(), v);

                //Ødelegger new_post fargmentet https://developer.android.com/guide/navigation/navigation-navigate#back-stack
                Navigation.findNavController(view).popBackStack();
            }
        });
        return root;
    }

    //Legger ned virtuelt tastatur https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String spinnerLabel = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
