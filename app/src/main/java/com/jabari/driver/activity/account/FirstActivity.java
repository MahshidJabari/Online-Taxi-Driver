package com.jabari.driver.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.jabari.driver.R;
import com.jabari.driver.activity.main.MainActivity;
import com.jabari.driver.global.GlobalVariables;
import com.jabari.driver.global.PrefManager;

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

        setPage();
        onClick();

    }

    private void onClick() {
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_reg);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
                startActivity(intent);
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

    private Boolean checkPref() {
        PrefManager prefManager = new PrefManager(getBaseContext());
        String token = "";
        token = prefManager.getTOken();

        if (TextUtils.isEmpty(token)) {
            return false;
        } else if (!TextUtils.isEmpty(token)) {
            GlobalVariables.tok = token;

        }
        return true;
    }

    private void removePreferences() {

        PrefManager prefManager = new PrefManager(this);
        prefManager.removeToken();
        prefManager.removeUser();
        GlobalVariables.tok = "";

    }

    private void setPage() {
        if (checkPref()) {
            GlobalVariables.isLogin = true;
            startActivity(new Intent(FirstActivity.this, MainActivity.class));

        } else {
            removePreferences();
        }
    }


}
