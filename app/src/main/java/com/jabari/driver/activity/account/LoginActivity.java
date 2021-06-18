package com.jabari.driver.activity.account;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.activity.main.MainActivity;
import com.jabari.driver.controller.LoginController;
import com.jabari.driver.global.ExceptionHandler;
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
    private ExceptionHandler handler;
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

        handler = new ExceptionHandler(this);
        setViews();
        fabOnclick();
        setFab_loginUnClickable();
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

    private void fabOnclick() {
        fab_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.getVerify) {
                    if (checkBox.isChecked())
                        check_login(et_phoneNum.getText().toString()
                                , et_verify_code.getText().toString());
                    else
                        handler.generateError("law");
                } else
                    handler.generateError("wrong code");
            }
        });

    }

    private boolean isValidPhone(String phone) {

        if (!Pattern.matches("^0(9)\\d{9}$", phone)) {
            handler.generateError("wrong phone");
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

    public void OnClickSendVerifyCode(final View view) {

        if (!isValidPhone(et_phoneNum.getText().toString()))
            handler.generateError("invalid phone");
        else {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.back_fifty_radius_gray));
            final String phoneNumber = et_phoneNum.getText().toString();
            ApiInterface.UserVerifyCodeCallback userVerifyCodeCallback = new ApiInterface.UserVerifyCodeCallback() {
                @Override
                public void onResponse(String success) {
                    GlobalVariables.getVerify = true;
                    setFab_loginClickable();
                    handler.generateSuccess(success);
                }

                @Override
                public void onFailure(String error) {
                    GlobalVariables.getVerify = false;
                    setFab_loginUnClickable();
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.back_fifty_radius_blue));
                    handler.generateError(error);
                }
            };
            LoginController loginController = new LoginController(userVerifyCodeCallback);
            loginController.VerifyCode(phoneNumber);

        }
    }

    private void check_login(final String phoneNum, final String verify_code) {


        ApiInterface.LoginUserCallback loginUserCallback = new ApiInterface.LoginUserCallback() {

            @Override
            public void onResponse(User user, String token) {

                saveLoginPrefrences(token, String.valueOf(user.getMobileNum()));
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(String error) {

                btn_send.setClickable(true);
                btn_send.setBackground(getResources().getDrawable(R.drawable.back_thirty_radius_blue));
                handler.generateError(error);

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

    public void getLaws(View view) {

        ApiInterface.GetLawsCallback getLawsCallback = new ApiInterface.GetLawsCallback() {
            @Override
            public void onResponse(String laws) {

                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.alertdialog);
                TextView body = dialog.findViewById(R.id.tv_dialog);
                body.setText(laws);
                Button button = dialog.findViewById(R.id.btn_ok);
                dialog.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);

            }
        };
        LoginController loginLawController = new LoginController(getLawsCallback);
        loginLawController.getLaws();
    }
}
