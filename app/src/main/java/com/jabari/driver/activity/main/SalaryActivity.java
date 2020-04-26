package com.jabari.driver.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.activity.account.ProfileActivity;
import com.jabari.driver.global.DigitConverter;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SalaryActivity extends AppCompatActivity {

    private FloatingActionButton fab_pro, fab_reward, fab_salary, fab_home;
    private TextView today_income,month_income,week_income,today_online,week_online,month_online
            ,whole_income,coms,days_remain;

    private TextView tv_return;
    private ImageView img_return;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        setView();
        onClickFab();
        setTextView();
        backOnclick();
    }

    private void setView() {

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

        today_income = findViewById(R.id.tv_today_income);
        today_online = findViewById(R.id.tv_today_online);
        week_income = findViewById(R.id.tv_week_income);
        week_online = findViewById(R.id.tv_week_online);
        month_income = findViewById(R.id.tv_month_income);
        month_online = findViewById(R.id.tv_month_online);
        whole_income = findViewById(R.id.tv_whole_income);
        coms = findViewById(R.id.tv_comis);
        days_remain = findViewById(R.id.tv_remain_day);


    }

    private void onClickFab(){
        //set on click listener to fab
        fab_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaryActivity.this, ProfileActivity.class);
                startActivity(intent);

            }
        });

        fab_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaryActivity.this, RewardActivity.class);
                startActivity(intent);

            }
        });

        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaryActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    private void setTextView(){

        today_income.setText(DigitConverter.convert("2000"));
        week_income.setText(DigitConverter.convert("2000"));
        month_income.setText(DigitConverter.convert("2000"));

        today_online.setText(DigitConverter.convert("20"));
        week_online.setText(DigitConverter.convert("20"));
        month_online.setText(DigitConverter.convert("20"));

        days_remain.setText(DigitConverter.convert("20"));
        coms.setText(DigitConverter.convert("2000"));
        whole_income.setText(DigitConverter.convert("2000"));

    }
    private void backOnclick(){
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SalaryActivity.this,MainActivity.class));
            }
        });
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SalaryActivity.this,MainActivity.class));
            }
        });

    }

}
