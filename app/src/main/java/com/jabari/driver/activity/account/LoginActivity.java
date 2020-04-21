package com.jabari.driver.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jabari.driver.R;
import com.jabari.driver.activity.MainActivity;
import com.jabari.driver.controller.LoginController;
import com.jabari.driver.global.GeneralResponse;
import com.jabari.driver.global.GlobalVariables;
import com.jabari.driver.global.PrefManager;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.User;

import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    private EditText et_phoneNum, et_verify_code;
    private Button btn_send;
    private FloatingActionButton fab_login;
    private CheckBox checkBox;
    private TextView tv_rules;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setViews();
        fabOnclick();
        setFab_loginUnClickable();
        directToWeb();
    }

    private void setViews() {
        et_phoneNum = findViewById(R.id.et_phoneNum);
        et_verify_code = findViewById(R.id.et_validationcode);
        btn_send = findViewById(R.id.btn_send);
        fab_login = findViewById(R.id.btn_login);
        fab_login.bringToFront();
        tv_rules = findViewById(R.id.tv_rul);
        checkBox = findViewById(R.id.checkbox);

    }

    private void directToWeb() {
        // tv_rules.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void fabOnclick() {
        fab_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.getVerify) {
                    check_login(et_phoneNum.getText().toString()
                            , et_verify_code.getText().toString());
                } else
                    Toast.makeText(LoginActivity.this, "کد فعالسازی به درستی وارد نشده!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isValidPhone(String phone) {

        if (!Pattern.matches("^0(9)\\d{9}$", phone)) {
            Toast.makeText(LoginActivity.this, "شماره ی موبایل اشتباه وارد شده!", Toast.LENGTH_LONG).show();
            et_phoneNum.getText().clear();
            btn_send.setBackground(getResources().getDrawable(R.drawable.back_thirty_radius_dark_gray));

            return false;
        } else
            return true;
    }

    private void setFab_loginClickable() {
        fab_login.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        fab_login.setClickable(true);

    }

    private void setFab_loginUnClickable() {

        fab_login.setBackgroundTintList(getResources().getColorStateList(R.color.darkGray));
        fab_login.setClickable(false);
    }

    public void OnClickSendVerifyCode(View view) {

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        if (!isValidPhone(et_phoneNum.getText().toString()))
            Toast.makeText(getBaseContext(), "شماره ی وارد شده معتبر نیست!", Toast.LENGTH_SHORT).show();
        else {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.back_fifty_radius_gray));
            final String phoneNumber = et_phoneNum.getText().toString();
            LoginController loginController = new LoginController(new ApiInterface.UserVerifyCodeCallback() {
                @Override
                public void onResponse(GeneralResponse generalResponse) {
                    GlobalVariables.getVerify = true;
                    setFab_loginClickable();
                    Toast.makeText(LoginActivity.this, "کد فعالسازی ارسال شد", Toast.LENGTH_LONG).show();
                    Toast.makeText(LoginActivity.this, phoneNumber.substring(6, 11), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(String error) {
                    GlobalVariables.getVerify = false;
                    setFab_loginUnClickable();
                    Toast.makeText(LoginActivity.this, "مجددا تلاش کنید", Toast.LENGTH_LONG).show();
                }
            });

            loginController.VerifyCode(phoneNumber);

        }
    }

    private void check_login(final String phoneNum, final String verify_code) {


        ApiInterface.LoginUserCallback loginUserCallback = new ApiInterface.LoginUserCallback() {

            @Override
            public void onResponse(GeneralResponse generalResponse, User user, String token) {


                if (generalResponse.getSuccess()) {

                    saveLoginPrefrences(token, String.valueOf(user.getMobileNum()));

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(String error) {

                btn_send.setClickable(true);
                btn_send.setBackground(getResources().getDrawable(R.drawable.back_thirty_radius_blue));

            }

        };

        LoginController loginUserController = new LoginController(loginUserCallback);
        loginUserController.Do(phoneNum, verify_code);
    }

    private void saveLoginPrefrences(String token, String user) {

        PrefManager prefManager = new PrefManager(getBaseContext());
        prefManager.setToken(token);
        prefManager.setPhoneNum(user);
        GlobalVariables.tok = token;
        GlobalVariables.phoneUser = user;

    }
}
