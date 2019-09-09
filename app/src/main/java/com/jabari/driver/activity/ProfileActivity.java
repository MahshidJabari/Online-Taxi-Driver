package com.jabari.driver.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jabari.driver.R;

public class ProfileActivity extends AppCompatActivity {
    private FloatingActionButton flo_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        flo_btn = findViewById(R.id.flo_btn);
        flo_btn.bringToFront();
    }
}
