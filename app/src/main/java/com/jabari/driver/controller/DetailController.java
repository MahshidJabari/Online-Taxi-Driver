package com.jabari.driver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.driver.network.config.ApiClient;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.Accounting;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailController {

    ApiInterface.StarCallback starCallback;
    ApiInterface.AccountingCallback accountingCallback;

    public DetailController(ApiInterface.StarCallback starCallback) {
        this.starCallback = starCallback;
    }

    public DetailController(ApiInterface.AccountingCallback accountingCallback) {
        this.accountingCallback = accountingCallback;
    }

    public void getStars() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.star();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

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
}
