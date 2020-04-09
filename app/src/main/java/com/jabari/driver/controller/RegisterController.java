package com.jabari.driver.controller;

import com.google.gson.JsonObject;
import com.jabari.driver.network.config.ApiClient;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterController {

    ApiInterface.SignUpCallback signUpCallback;

    public RegisterController(ApiInterface.SignUpCallback signUpCallback) {
        this.signUpCallback = signUpCallback;
    }

    public void Do(User user) {

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.sign_up(user.getFirstName(),
                user.getLastName(), user.getMeli(), user.getIdentity(),
                user.getAge(), user.getAddress(), user.getSheba(), user.getMobileNum());
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
