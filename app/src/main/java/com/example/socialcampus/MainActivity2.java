package com.example.socialcampus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import custom_font.MyTextView;
import custom_font.MyTextView;

public class MainActivity2 extends AppCompatActivity {


    MyTextView signin1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signupe);


        signin1 = (MyTextView)findViewById(R.id.signin1);

        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(it);
            }
        });
    }
}
