package com.jabari.driver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.activity.main.MainActivity;
import com.jabari.driver.controller.DetailController;
import com.jabari.driver.controller.UserController;
import com.jabari.driver.global.DigitConverter;
import com.jabari.driver.global.ExceptionHandler;
import com.jabari.driver.network.config.ApiInterface;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ScoreActivity extends AppCompatActivity {

    private TextView tv_score, tv_travels, tv_stars;
    private ExceptionHandler handler;
    private RatingBar ratingBar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        handler = new ExceptionHandler(this);
        setViews();
        getStar();
    }

    public void onBack(View view) {
        startActivity(new Intent(ScoreActivity.this, MainActivity.class));
    }

    private void setViews() {
        tv_score = findViewById(R.id.tv_score);
        tv_stars = findViewById(R.id.tv_stars);
        tv_travels = findViewById(R.id.tv_travels);
        ratingBar = findViewById(R.id.ratingBar);
    }


    private void getStar() {
        ApiInterface.StarCallback starCallback = new ApiInterface.StarCallback() {
            @Override
            public void onResponse(String starCount, String tripCount, String currentStar) {
                tv_score.setText(DigitConverter.convert(currentStar));
                tv_stars.setText(DigitConverter.convert(starCount + "عدد"));
                tv_travels.setText(DigitConverter.convert(tripCount + "عدد"));
                ratingBar.setNumStars(Integer.parseInt(currentStar));
                ratingBar.setRating(Integer.parseInt(currentStar));

            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);
            }
        };
        DetailController detailController = new DetailController(starCallback);
        detailController.getStars();
    }
}
