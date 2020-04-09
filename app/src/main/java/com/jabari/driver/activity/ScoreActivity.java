package com.jabari.driver.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.global.DigitConverter;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ScoreActivity extends AppCompatActivity {

    private TextView tv_score,tv_travels,tv_stars;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        setViews();
        setTextView();
    }

    private void setViews(){
        tv_score = findViewById(R.id.tv_score);
        tv_stars = findViewById(R.id.tv_stars);
        tv_travels = findViewById(R.id.tv_travels);
    }

    private void setTextView(){
        tv_score.setText(DigitConverter.convert("4.75/5"));
        tv_stars.setText(DigitConverter.convert("125 عدد"));
        tv_travels.setText(DigitConverter.convert("125 عدد"));

    }
}
