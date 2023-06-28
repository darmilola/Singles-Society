package com.singlesSociety;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MetMatchPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_met_match_page);
    }
}
