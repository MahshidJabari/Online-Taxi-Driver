package com.jabari.driver.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jabari.driver.R;

public class LoginActivity extends AppCompatActivity {

    private EditText et_phoneNum,et_validationcode;
    private Button btn_send,btn_login;
    private String phone_num,validationcode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set views
        et_phoneNum = (EditText) findViewById(R.id.et_phoneNum);
        et_validationcode = (EditText)findViewById(R.id.et_validationcode);
        btn_send = (Button)findViewById(R.id.btn_send);
        btn_login = (Button) findViewById(R.id.btn_login);

        phone_num = et_phoneNum.getText().toString();
        validationcode = et_validationcode.getText().toString();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendValidationCode(phone_num);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_login(validationcode);
            }
        });
    }

    public void check_login(String validation_code){

    }
    public void sendValidationCode(String phoneNum){

    }

  }
