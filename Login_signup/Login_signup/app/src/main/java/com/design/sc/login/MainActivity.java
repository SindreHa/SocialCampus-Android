package com.design.sc.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import custom_font.MyTextView;


public class MainActivity extends AppCompatActivity {
TextView zoo;
    MyTextView create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Typeface custom_fonts = Typeface.createFromAsset(getAssets(), "fonts/Swistblnk Duwhoers Brush.ttf");
        zoo = (TextView)findViewById(R.id.socialcampus);
        zoo.setTypeface(custom_fonts);

        create = (MyTextView)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(it);

            }
        });
    }
}
