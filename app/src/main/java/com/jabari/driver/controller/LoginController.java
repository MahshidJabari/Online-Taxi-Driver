package com.jabari.driver.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.driver.global.GeneralResponse;
import com.jabari.driver.global.GlobalVariables;
import com.jabari.driver.network.config.ApiClient;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginController {

    ApiInterface.LoginUserCallback loginUserCallback;
    ApiInterface.UserVerifyCodeCallback userVerifyCodeCallback;
    ApiInterface.CurrentUserCallback currentUserCallback;


    public LoginController(ApiInterface.UserVerifyCodeCallback userVerifyCodeCallback) {
        this.userVerifyCodeCallback = userVerifyCodeCallback;
    }

    public LoginController(ApiInterface.LoginUserCallback loginUserCallback) {
        this.loginUserCallback = loginUserCallback;
    }

    public LoginController(ApiInterface.CurrentUserCallback currentUserCallback) {
        this.currentUserCallback = currentUserCallback;
    }

    public void Do(final String mobileNum, String verify_code) {
        User user = new User();
        user.setMobileNum(mobileNum);
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterfaces.check_login(mobileNum, verify_code);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    GeneralResponse generalResponse = new GeneralResponse(response.body());
                    User user = new Gson().fromJson(response.body().get("user"), User.class);
                    String token = new Gson().fromJson(response.body().get("jwtAccessToken"), String.class);
                    GlobalVariables.tok = token;
                    Log.d("tok", token);

                    loginUserCallback.onResponse(generalResponse, user, token);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loginUserCallback.onFailure("Error!");
            }
        });
    }

    public void VerifyCode(String PhoneNumber) {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.getVerifyCode(PhoneNumber);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    GeneralResponse generalResponse = new GeneralResponse(response.body());
                    userVerifyCodeCallback.onResponse(generalResponse);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                userVerifyCodeCallback.onFailure("Error!");
            }
        });
    }

    public void getCurrentUser() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.currentUser();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    User user = new Gson().fromJson(response.body().get("user"), User.class);
                    currentUserCallback.onResponse(user);
                } else
                    currentUserCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                currentUserCallback.onFailure("connection");
            }
        });
    }
}