package com.jabari.driver.activity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.activity.account.ProfileActivity;
import com.karumi.dexter.BuildConfig;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab_pro, fab_reward, fab_salary, fab_home;
    private LinearLayout ln_finance, ln_score, ln_travel_list, ln_area, ln_salary;
    private CheckBox check_net, check_GPS;
    private TextView tv_on_off;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();
        setCheck_net();
    }

    public void setView() {

        //set tv
        tv_on_off = findViewById(R.id.tv_online_offline);

        //set check box
        check_net = findViewById(R.id.check_net);
        check_GPS = findViewById(R.id.check_GPS);
        if (check_net.isChecked()) {

            tv_on_off.setText("آنلاین");

        } else
            tv_on_off.setText("آفلاین");

        if (check_GPS.isChecked()) {
            openSettings();
        }


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
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);

            }
        });

        fab_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SalaryActivity.class);
                startActivity(intent);

            }
        });

        fab_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RewardActivity.class);
                startActivity(intent);

            }
        });

        //set linear layout view
        ln_finance = findViewById(R.id.ln_finance);
        ln_score = findViewById(R.id.ln_score);
        ln_travel_list = findViewById(R.id.ln_travel_list);
        ln_area = findViewById(R.id.ln_area);
        ln_salary = findViewById(R.id.ln_salary);

        //set on click listener to linear layouts
        ln_finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SalaryDetailActivity.class);
                startActivity(intent);
            }
        });

        ln_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(intent);

            }
        });
        ln_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AreaActivity.class);
                startActivity(intent);

            }
        });
        ln_travel_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);

            }
        });
        ln_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SalaryActivity.class);
                startActivity(intent);

            }
        });

    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void setCheck_net() {
        if (isNetworkConnected()) {
            check_net.setChecked(true);
        } else
            check_net.setChecked(false);
    }

    private boolean isGPSConnected(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            return false;
           }
        return true;
    }
}
