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
import com.jabari.driver.controller.DetailController;
import com.jabari.driver.global.DigitConverter;
import com.jabari.driver.global.ExceptionHandler;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.Accounting;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SalaryActivity extends AppCompatActivity {

    private FloatingActionButton fab_pro, fab_reward, fab_salary, fab_home;
    private TextView today_income, month_income, week_income, today_online, week_online, month_online, whole_income, commission, days_remain;

    private TextView tv_return;
    private ImageView img_return;
    private ExceptionHandler handler;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salery);
        handler = new ExceptionHandler(this);

        setView();
        onClickFab();
        backOnclick();
        getAccounting();
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
        commission = findViewById(R.id.tv_comis);
        days_remain = findViewById(R.id.tv_remain_day);


    }

    private void onClickFab() {
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


    private void backOnclick() {
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SalaryActivity.this, MainActivity.class));
            }
        });
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SalaryActivity.this, MainActivity.class));
            }
        });

    }

    private void getAccounting() {
        ApiInterface.AccountingCallback accountingCallback = new ApiInterface.AccountingCallback() {
            @Override
            public void onResponse(Accounting accounting) {
                setText(accounting);
            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);
            }
        };
        DetailController detailController = new DetailController(accountingCallback);
        detailController.getAccounting();
    }

    private void setText(Accounting accounting) {
        today_income.setText(DigitConverter.convert(accounting.getDayIncome()));
        week_income.setText(DigitConverter.convert(accounting.getWeekIncome()));
        month_income.setText(DigitConverter.convert(accounting.getMonthIncome()));
        today_online.setText(DigitConverter.convert(accounting.getDayVisible()));
        week_online.setText(DigitConverter.convert(accounting.getWeekVisible()));
        month_online.setText(DigitConverter.convert(accounting.getMonthVisible()));
        whole_income.setText(DigitConverter.convert(accounting.getTotalIncome()));
        commission.setText(DigitConverter.convert(accounting.getTotalCommission()));
        days_remain.setText(DigitConverter.convert(accounting.getCommissionDayLeft()));
    }

}
