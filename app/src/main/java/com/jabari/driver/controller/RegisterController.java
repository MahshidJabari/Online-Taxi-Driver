package com.jabari.driver.controller;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jabari.driver.global.GlobalVariables;
import com.jabari.driver.network.config.ApiClient;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.User;

import java.io.File;

import me.cheshmak.android.sdk.core.Cheshmak;
import me.cheshmak.android.sdk.core.network.CheshmakCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterController {

    ApiInterface.SignUpCallback signUpCallback;
    ApiInterface.UploadFileCallback uploadFileCallback;
    Context context;
    private String cheshmakId;

    public RegisterController(ApiInterface.SignUpCallback signUpCallback) {
        this.signUpCallback = signUpCallback;
    }

    public RegisterController(ApiInterface.UploadFileCallback uploadFileCallback, Context context) {
        this.uploadFileCallback = uploadFileCallback;
        this.context = context;
    }

    public void getNotifyId() {

        Cheshmak.initTracker("r035KbHW8OcMwBQhPs+Jpg==", new CheshmakCallback() {
            @Override
            public void onCheshmakIdReceived(String cheshmakID) {
                cheshmakId = cheshmakID;
            }
        });

    }

    public void signUp(User user) {

        getNotifyId();
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.sign_up(user.getAge(), user.getEmail(),
                user.getName(), user.getFatherName(), "false", user.getMobileNum(), user.getAddress(),
                user.getNationalNumber(), user.getIdentity(), user.getSheba(), GlobalVariables.vehicle, cheshmakId);
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

    public void upload(String s) {

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        File f = new File(s);
        Log.d("s", s);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", f.getName(), reqFile);


        Call<JsonObject> call = apiInterface.uploadPhotos(image, GlobalVariables.tok);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d("response", response.toString());
                if (response.body() != null) {
                    String url = new Gson().fromJson(response.body().get("url"), String.class);
                    String uploaded_fileUrl = "http://digipeyk.com/" + url;
                    uploadFileCallback.onResponse(uploaded_fileUrl);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                uploadFileCallback.onFailure("connection");
            }
        });

    }
}
