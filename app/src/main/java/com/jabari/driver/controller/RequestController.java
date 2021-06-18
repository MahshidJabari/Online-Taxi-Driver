package com.jabari.driver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.driver.network.config.ApiClient;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.History;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public void getReadyRequests(String id) {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.readyRequests(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    History history = new Gson().fromJson(response.body().get("request"), History.class);
                    requestCallback.onResponse(history);
                } else requestCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                requestCallback.onFailure("connection");
            }
        });
    }

    public void acceptRequest(String id) {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.acceptedRequest(id);
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
