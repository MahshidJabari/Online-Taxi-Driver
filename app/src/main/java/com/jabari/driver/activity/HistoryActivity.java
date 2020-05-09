package com.jabari.driver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.jabari.driver.R;
import com.jabari.driver.activity.main.MainActivity;
import com.jabari.driver.activity.main.TravelDetailActivity;
import com.jabari.driver.adapter.HistoryAdapter;
import com.jabari.driver.controller.DetailController;
import com.jabari.driver.global.ExceptionHandler;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.History;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView history_recycler;
    private ExceptionHandler handler;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        handler = new ExceptionHandler(this);
        getHistory();
    }

    private void setRecyclerView(final ArrayList<History> histories) {
        history_recycler = findViewById(R.id.recycler_history);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        HistoryAdapter adapter = new HistoryAdapter(this, history_recycler, histories, new HistoryAdapter.HistoryAdapterListener() {
            @Override
            public void detailOnClick(View v, int position) {
                Intent intent = new Intent(HistoryActivity.this, TravelDetailActivity.class);
                intent.putExtra("history", histories.get(position));
                startActivity(intent);
            }
        });
        history_recycler.setAdapter(adapter);
        history_recycler.setLayoutManager(manager);
    }

    public void onBackClick(View view) {
        startActivity(new Intent(HistoryActivity.this, MainActivity.class));
    }

    private void getHistory() {
        ApiInterface.HistoryCallback historyCallback = new ApiInterface.HistoryCallback() {
            @Override
            public void onResponse(ArrayList<History> historyList) {
                setRecyclerView(historyList);
            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);
            }
        };
        DetailController detailController = new DetailController(historyCallback);
        detailController.getHistory();
    }


}
