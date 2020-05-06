package com.jabari.driver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.activity.main.MainActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView hisory_recycler;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setView();
    }

    private void setView() {
        hisory_recycler = findViewById(R.id.recycler_history);
    }

    public void onBackClick(View view) {
        startActivity(new Intent(HistoryActivity.this, MainActivity.class));
    }


}
