package com.jabari.driver.controller;

import com.google.gson.JsonObject;
import com.jabari.driver.network.config.ApiClient;
import com.jabari.driver.network.config.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LocationController {

    ApiInterface.GetPointsCallback getPointsCallback;

    public LocationController(ApiInterface.GetPointsCallback getPointsCallback){

        this.getPointsCallback = getPointsCallback;
    }

    public void Do(){
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterfaces.getLatLong();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
}
