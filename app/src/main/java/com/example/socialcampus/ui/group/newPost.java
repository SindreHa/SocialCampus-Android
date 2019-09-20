package com.example.socialcampus.ui.group;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialcampus.R;

/**
 * Created by Sindre Haavaldsen on 17.09.19.
 */
public class newPost extends Fragment implements AdapterView.OnItemSelectedListener {

    private CardView sendPostButton;
    private TextView title;
    private TextView comment;

    public newPost() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.new_post, container, false);

        sendPostButton = root.findViewById(R.id.send_post_button);
        title = root.findViewById(R.id.new_post_title);
        comment = root.findViewById(R.id.new_post_content);

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

        sendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "yo", Toast.LENGTH_LONG).show();
            }
        });



        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String spinnerLabel = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void sendPostToGroup(){

    }
}
