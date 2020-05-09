package com.jabari.driver.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.driver.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AccountActivity extends AppCompatActivity {
    private EditText et_sheba, et_name;
    private Button btn_remove;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        setView();
    }

    private void setView() {
        et_sheba = findViewById(R.id.et_account_num);
        et_name = findViewById(R.id.et_account_name);
        btn_remove = findViewById(R.id.btn_remove_sheba);
        /////set editText not editable
        et_sheba.setTag(et_sheba.getKeyListener());
        et_sheba.setKeyListener(null);
        et_name.setTag(et_name.getKeyListener());
        et_name.setKeyListener(null);
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

    }

    public void setEditable(View view) {
        et_sheba.setKeyListener((KeyListener) et_sheba.getTag());
        et_name.setKeyListener((KeyListener) et_name.getTag());
    }
}
