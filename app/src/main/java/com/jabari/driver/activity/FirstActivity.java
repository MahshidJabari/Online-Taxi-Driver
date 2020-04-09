package com.jabari.driver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jabari.driver.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FirstActivity extends AppCompatActivity {
    private Button btn_login, btn_register;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_reg);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
                startActivity(intent);
                //TO DO: check if registered or not
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }
}
