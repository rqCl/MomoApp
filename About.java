package com.example.navigationsandtabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView description = findViewById(R.id.description);
        String descriptionText = "An app that dares the user to do eco-friendly challenges in exchange of points. This newly-created program will help people develop the sense of responsibility in taking care of the environment through simple things that they can do in their daily lives. It does not only spread awareness but drives the individual to take action and help sustain nature hands-on.";
        description.setText(descriptionText);
    }
}
