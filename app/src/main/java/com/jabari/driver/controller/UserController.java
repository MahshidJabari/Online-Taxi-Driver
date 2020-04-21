package com.jabari.driver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.driver.network.config.ApiClient;
import com.jabari.driver.network.config.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserController {

    ApiInterface.callSupportCallback callSupportCallback;

    public UserController(ApiInterface.callSupportCallback callSupportCallback) {
        this.callSupportCallback = callSupportCallback;
    }

    public void call() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterfaces.call_support();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    String phone = new Gson().fromJson(response.body().get("phoneNumber"), String.class);
                    callSupportCallback.onResponse(phone);
                } else
                    callSupportCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callSupportCallback.onFailure("connection");
            }
        });
    }
}
