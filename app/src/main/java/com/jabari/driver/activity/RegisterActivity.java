package com.jabari.driver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.controller.RegisterController;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.User;

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

    private void setUpView(){
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

    private User createUser(){

        user = new User();
        user.setAddress(et_address.getText().toString());
        user.setAge(et_age.getText().toString());
        user.setFirstName(et_name.getText().toString());
        user.setLastName(et_fName.getText().toString());
        user.setMeli(et_nationalcode.getText().toString());
        user.setIdentity(et_personalcode.getText().toString());
        user.setMobileNum(et_phone.getText().toString());
        user.setSheba(et_sheba.getText().toString());
        return user;
    }

    private void onBackClick(){
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,FirstActivity.class));
            }
        });
    }

    public void OnRegisterClick(View view){

        startActivity(new Intent(RegisterActivity.this,MainActivity.class));

       /* ApiInterface.SignUpCallback signUpCallback = new ApiInterface.SignUpCallback() {
            @Override
            public void onResponse(String token) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }

            @Override
            public void onFailure(String error) {

            }
        };

        RegisterController registerController = new RegisterController(signUpCallback);
        registerController.Do(createUser());
   */ }

}
