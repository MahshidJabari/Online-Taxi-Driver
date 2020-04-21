package com.jabari.driver.controller;

import com.google.gson.Gson;
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

    public void signUp(User user) {

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.sign_up(user.getAge(), user.getEmail(),
                user.getName(), user.getFatherName(), "false", user.getMobileNum(), user.getAddress(),
                user.getNationalNumber(), user.getIdentity(), user.getSheba());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    String token = new Gson().fromJson(response.body().get("jwtAccessToken"), String.class);
                    signUpCallback.onResponse(token);

                } else
                    signUpCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                signUpCallback.onFailure("connection");
            }
        });

    }
}
