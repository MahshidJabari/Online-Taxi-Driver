package com.jabari.driver.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.driver.R;
import com.jabari.driver.controller.UserController;
import com.jabari.driver.global.ExceptionHandler;
import com.jabari.driver.global.GlobalVariables;
import com.jabari.driver.global.PrefManager;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.Document;
import com.jabari.driver.network.model.User;
import com.jabari.driver.network.model.Vehicle;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AccountActivity extends AppCompatActivity {
    private EditText et_sheba;
    private TextView tv_name;
    private Button btn_remove;
    private Document document;
    private User user;
    private ExceptionHandler handler;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        handler = new ExceptionHandler(this);

        setView();
        getPref();
    }

    private void setView() {
        et_sheba = findViewById(R.id.et_account_num);
        tv_name = findViewById(R.id.tv_account_name);
        btn_remove = findViewById(R.id.btn_remove_sheba);
        tv_name.setText(GlobalVariables.name);
        /////set editText not editable
        et_sheba.setTag(et_sheba.getKeyListener());
        et_sheba.setKeyListener(null);
        /////remove listener
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_sheba.setText("");
            }
        });
    }

    public void OnBackClick(View view) {
        startActivity(new Intent(AccountActivity.this, ProfileActivity.class));
    }

    public void OnConfirmClick(View view) {
        user.setSheba(et_sheba.getText().toString());
        updateSheba();
    }

    public void setEditable(View view) {
        et_sheba.setKeyListener((KeyListener) et_sheba.getTag());
    }

    private void getPref() {
        PrefManager prefManager = new PrefManager(getBaseContext());
        document = new Document();
        document.setDocumentMeli(prefManager.getDocMeli());
        document.setDocumentId(prefManager.getId());
        document.setDocumentMilitary(prefManager.getMilitary());
        document.setDocumentElectricalBill(prefManager.getElectricBill());
        document.setDocumentWaterBill(prefManager.getWaterBill());
        document.setDocumentGreenPaper(prefManager.getGreenPaper());
        document.setDocumentLicense(prefManager.getLicence());
        user = new User();
        user.setMobileNum(GlobalVariables.phoneUser);
        user.setEmail(GlobalVariables.email);
        user.setName(GlobalVariables.name);
        Vehicle vehicle = new Vehicle();
        vehicle.setTitle(GlobalVariables.vehicle);
        user.setVehicle(vehicle);
    }
    private void updateSheba(){
        ApiInterface.UpdateDriverCallback updateDriverCallback = new ApiInterface.UpdateDriverCallback() {
            @Override
            public void onResponse() {
                handler.generateSuccess("sheba");
            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);
            }
        };
        UserController userController = new UserController(updateDriverCallback);
        userController.updateDriver(user,document);
    }

}
