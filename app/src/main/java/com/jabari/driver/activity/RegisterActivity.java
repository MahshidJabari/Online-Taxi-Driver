package com.jabari.driver.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.jabari.driver.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_name , et_fName , et_nationalcode,et_personalcode,et_age,et_address,et_shaba,et_email,et_phone;
    private String name , fName , nationalcode, personalcode, age, address, shaba, email,phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_name = (EditText)findViewById(R.id.et_name);
        et_fName = (EditText) findViewById(R.id.et_fName);
        et_nationalcode = (EditText)findViewById(R.id.et_nationalcode);
        et_personalcode = (EditText) findViewById(R.id.et_personalcode);
        et_age = (EditText)findViewById(R.id.et_age);
        et_address = (EditText) findViewById(R.id.et_address);
        et_shaba = (EditText)findViewById(R.id.et_numShaba);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phone = (EditText) findViewById(R.id.et_phone);

    }

}
