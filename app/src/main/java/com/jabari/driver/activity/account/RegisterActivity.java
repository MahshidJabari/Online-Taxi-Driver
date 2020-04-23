package com.jabari.driver.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.activity.main.MainActivity;
import com.jabari.driver.controller.RegisterController;
import com.jabari.driver.global.GlobalVariables;
import com.jabari.driver.global.PrefManager;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.User;

import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_name, et_fName, et_nationalcode, et_personalcode, et_age, et_address, et_sheba, et_phone;
    private TextView tv_return;
    User user;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setUpView();
        onBackClick();

    }

    private void setUpView() {
        et_name = findViewById(R.id.et_name);
        et_fName = findViewById(R.id.et_fName);
        et_nationalcode = findViewById(R.id.et_nationalcode);
        et_personalcode = findViewById(R.id.et_personalcode);
        et_age = findViewById(R.id.et_age);
        et_address = findViewById(R.id.et_address);
        et_sheba = findViewById(R.id.et_numShaba);
        et_phone = findViewById(R.id.et_phone);
        tv_return = findViewById(R.id.tv_return);

    }

    private User createUser() {

        user = new User();
        user.setAddress(et_address.getText().toString());
        user.setAge(et_age.getText().toString());
        user.setFatherName(et_fName.getText().toString());
        user.setNationalNumber(et_nationalcode.getText().toString());
        user.setIdentity(et_personalcode.getText().toString());
        user.setMobileNum(et_phone.getText().toString());
        user.setSheba(et_sheba.getText().toString());
        user.setName(et_name.getText().toString());
        return user;
    }

    private void onBackClick() {
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, FirstActivity.class));
            }
        });
    }

    public void OnRegisterClick(View view) {

        ApiInterface.SignUpCallback signUpCallback = new ApiInterface.SignUpCallback() {
            @Override
            public void onResponse(String token) {
                savePreferences(token);
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(String error) {
                if (error.equals("connection"))
                    Toasty.error(RegisterActivity.this, "خطا در برقراری ارتباط!", Toasty.LENGTH_LONG).show();
                if (error.equals("null"))
                    Toasty.error(RegisterActivity.this, "درخواست با خطا مواجه شد!", Toasty.LENGTH_LONG).show();
            }
        };

        RegisterController registerController = new RegisterController(signUpCallback);
        registerController.signUp(createUser());
    }

    private void savePreferences(String token) {

        PrefManager prefManager = new PrefManager(getBaseContext());
        prefManager.setToken(token);
        GlobalVariables.tok = token;

    }

}
