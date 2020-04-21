package com.jabari.driver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.activity.account.ProfileActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RewardActivity extends AppCompatActivity {
    private FloatingActionButton fab_pro, fab_reward, fab_salary, fab_home;
    private TextView tv_return;
    private ImageView img_return;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        setView();
        backOnclick();
    }

    public void setView() {

        tv_return = findViewById(R.id.tv_return);
        img_return = findViewById(R.id.img_return);

        //set FloatingActionButton view
        fab_pro = findViewById(R.id.fab_pro);
        fab_reward = findViewById(R.id.fab_reward);
        fab_salary = findViewById(R.id.fab_salary);
        fab_home = findViewById(R.id.fab_home);
        fab_pro.bringToFront();
        fab_reward.bringToFront();
        fab_salary.bringToFront();
        fab_home.bringToFront();

        //set on click listener to fab
        fab_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RewardActivity.this, ProfileActivity.class);
                startActivity(intent);

            }
        });

        fab_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RewardActivity.this, SalaryActivity.class);
                startActivity(intent);

            }
        });

        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RewardActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
    private void backOnclick(){
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RewardActivity.this,MainActivity.class));
            }
        });
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RewardActivity.this,MainActivity.class));
            }
        });

    }


}
