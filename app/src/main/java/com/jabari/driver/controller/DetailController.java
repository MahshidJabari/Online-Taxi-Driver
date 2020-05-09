package com.jabari.driver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jabari.driver.network.config.ApiClient;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.Accounting;
import com.jabari.driver.network.model.History;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailController {

    ApiInterface.StarCallback starCallback;
    ApiInterface.AccountingCallback accountingCallback;
    ApiInterface.HistoryCallback historyCallback;

    public DetailController(ApiInterface.StarCallback starCallback) {
        this.starCallback = starCallback;
    }

    public DetailController(ApiInterface.AccountingCallback accountingCallback) {
        this.accountingCallback = accountingCallback;
    }

    public DetailController(ApiInterface.HistoryCallback historyCallback) {
        this.historyCallback = historyCallback;
    }

    public void getStars() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.star();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    String starCount = new Gson().fromJson(response.body().get("starCount"), String.class);
                    String tripCount = new Gson().fromJson(response.body().get("tripCount"), String.class);
                    String currentStar = new Gson().fromJson(response.body().get("currentStar"), String.class);
                    starCallback.onResponse(starCount, tripCount, currentStar);
                } else starCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                starCallback.onFailure("connection");
            }
        });
    }

    public void getAccounting() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Accounting> call = apiInterface.accounting();
        call.enqueue(new Callback<Accounting>() {
            @Override
            public void onResponse(Call<Accounting> call, Response<Accounting> response) {
                Accounting accounting = new Accounting();
                if (response.body() != null) {
                    accounting.setDayIncome(new Gson().fromJson(response.body().getDayIncome(), String.class));
                    accounting.setWeekIncome(new Gson().fromJson(response.body().getWeekIncome(), String.class));
                    accounting.setMonthIncome(new Gson().fromJson(response.body().getMonthIncome(), String.class));
                    accounting.setDayVisible(new Gson().fromJson(response.body().getDayVisible(), String.class));
                    accounting.setWeekVisible(new Gson().fromJson(response.body().getWeekVisible(), String.class));
                    accounting.setMonthVisible(new Gson().fromJson(response.body().getMonthVisible(), String.class));
                    accounting.setCommissionPercent(new Gson().fromJson(response.body().getCommissionPercent(), String.class));
                    accounting.setCommissionDayLeft(new Gson().fromJson(response.body().getCommissionDayLeft(), String.class));
                    accounting.setTotalIncome(new Gson().fromJson(response.body().getTotalIncome(), String.class));
                    accounting.setTotalCommission(new Gson().fromJson(response.body().getTotalCommission(), String.class));
                    accountingCallback.onResponse(accounting);
                } else
                    accountingCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<Accounting> call, Throwable t) {
                accountingCallback.onFailure("connection");
            }
        });
    }

    public void getHistory() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.history();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Type requestListType = new TypeToken<ArrayList<History>>() {
                    }.getType();
                    ArrayList<History> requests = new Gson().fromJson(response.body().get("requests"), requestListType);
                    historyCallback.onResponse(requests);
                } else historyCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                historyCallback.onFailure("connection");
            }
        });
    }
}
