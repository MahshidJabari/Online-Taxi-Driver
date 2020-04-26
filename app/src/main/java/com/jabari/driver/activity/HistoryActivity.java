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
    private TextView tv_return;
    private ImageView img_return;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setView();
        backOnclick();
    }

    private void setView(){
        hisory_recycler = findViewById(R.id.recycler_history);
        tv_return = findViewById(R.id.tv_return);
        img_return = findViewById(R.id.img_return);
    }
    private void backOnclick(){
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryActivity.this, MainActivity.class));
            }
        });
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryActivity.this,MainActivity.class));
            }
        });

    }


}
