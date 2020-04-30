package com.jabari.driver.controller;

import com.google.gson.JsonObject;
import com.jabari.driver.network.config.ApiClient;
import com.jabari.driver.network.config.ApiInterface;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RequestController {
    ApiInterface.AcceptedRequestCallback acceptedRequestCallback;
    ApiInterface.RequestCallback requestCallback;

    public RequestController(ApiInterface.RequestCallback requestCallback) {
        this.requestCallback = requestCallback;
    }

    public RequestController(ApiInterface.AcceptedRequestCallback acceptedRequestCallback) {
        this.acceptedRequestCallback = acceptedRequestCallback;
    }

    public void getReadyRequests(){
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.readyRequests();
    }
}
