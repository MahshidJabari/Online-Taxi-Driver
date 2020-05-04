package com.jabari.driver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.driver.network.config.ApiClient;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.Coordinate;
import com.jabari.driver.network.model.Document;
import com.jabari.driver.network.model.User;

import me.cheshmak.android.sdk.core.Cheshmak;
import me.cheshmak.android.sdk.core.network.CheshmakCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserController {

    ApiInterface.callSupportCallback callSupportCallback;
    ApiInterface.UpdateLocationCallback updateLocationCallback;
    ApiInterface.VisibilityCallback visibilityCallback;
    ApiInterface.CurrentUserCallback currentUserCallback;
    ApiInterface.UpdateDriverCallback updateDriverCallback;
    private String cheshmakId;

    public UserController(ApiInterface.callSupportCallback callSupportCallback) {
        this.callSupportCallback = callSupportCallback;
    }

    public UserController(ApiInterface.UpdateLocationCallback updateLocationCallback) {
        this.updateLocationCallback = updateLocationCallback;
    }

    public UserController(ApiInterface.VisibilityCallback visibilityCallback) {
        this.visibilityCallback = visibilityCallback;
    }

    public UserController(ApiInterface.CurrentUserCallback currentUserCallback) {
        this.currentUserCallback = currentUserCallback;
    }

    public UserController(ApiInterface.UpdateDriverCallback updateDriverCallback) {
        this.updateDriverCallback = updateDriverCallback;
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

    public void updateLocation(Coordinate coordinate) {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Coordinate> call = apiInterface.updateUserLocation(coordinate.getLatitude(), coordinate.getLongitude());
        call.enqueue(new Callback<Coordinate>() {
            @Override
            public void onResponse(Call<Coordinate> call, Response<Coordinate> response) {
                if (response.body() != null) {
                    String latitude = new Gson().fromJson(response.body().getLatitude().toString(), String.class);
                    updateLocationCallback.onResponse();
                } else
                    updateLocationCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<Coordinate> call, Throwable t) {
                updateLocationCallback.onFailure("connection");
            }
        });
    }

    public void setVisibility(Coordinate coordinate, Boolean visibility) {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.setVisibility(visibility, coordinate.getLatitude(), coordinate.getLongitude());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    visibilityCallback.onResponse("visibility");

                } else
                    visibilityCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                visibilityCallback.onFailure("connection");
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
                currentUserCallback.onFailure("anonymous");
            }
        });
    }

    public void getNotifyId() {

        Cheshmak.initTracker("r035KbHW8OcMwBQhPs+Jpg==", new CheshmakCallback() {
            @Override
            public void onCheshmakIdReceived(String cheshmakID) {
                cheshmakId = cheshmakID;
            }
        });

    }

    public void updateDriver(User user, Document document) {
        getNotifyId();
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.updateDriver(user.getEmail(), user.getName(), "false", user.getMobileNum(), cheshmakId,
                document.getDocumentMeli(), document.getDocumentId(), document.getDocumentLicense(), document.getDocumentMilitary(),
                document.getDocumentGreenPaper(), document.getDocumentWaterBill(), document.getDocumentElectricalBill());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    updateDriverCallback.onResponse();
                } else
                    updateDriverCallback.onFailure("null");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                updateDriverCallback.onFailure("connection");
            }
        });
    }
}
