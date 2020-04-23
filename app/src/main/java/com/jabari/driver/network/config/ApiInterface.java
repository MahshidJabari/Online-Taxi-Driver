package com.jabari.driver.network.config;

import android.location.Location;

import com.google.gson.JsonObject;
import com.jabari.driver.global.GeneralResponse;
import com.jabari.driver.network.model.Coordinate;
import com.jabari.driver.network.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("auth/driver/login/")
    Call<JsonObject> getVerifyCode(@Field("mobile") String PhoneNumber);

    interface UserVerifyCodeCallback {
        void onResponse(GeneralResponse generalResponse);

        void onFailure(String error);
    }

    @FormUrlEncoded
    @POST("auth/driver/loginverify")
    Call<JsonObject> check_login(@Field("mobile") String PhoneNumber,
                                 @Field("verifyCode") String verifyCode);

    interface LoginUserCallback {
        void onResponse(GeneralResponse generalResponse, User user, String token);

        void onFailure(String error);
    }

    @FormUrlEncoded
    @POST("auth/driver/getlatlong")
    Call<JsonObject> getLatLong();

    interface GetPointsCallback {
        void onResponse(Location start, Location stop);

        void onFailure(String error);
    }

    @FormUrlEncoded
    @POST("auth/driver/signup")
    Call<JsonObject> sign_up(@Field("age") String age,
                             @Field("email") String email,
                             @Field("name") String name,
                             @Field("fatherName") String LastName,
                             @Field("gender") String gender,
                             @Field("mobile") String mobile,
                             @Field("address") String address,
                             @Field("nationalNumber") String nationalNumber,
                             @Field("idNumber") String idNumber,
                             @Field("shebaNumber") String shebaNumber);

    interface SignUpCallback {
        void onResponse(String token);

        void onFailure(String error);
    }

    @GET("driver")
    Call<JsonObject> currentUser();

    interface CurrentUserCallback {
        void onResponse(User user);

        void onFailure(String error);
    }

    @GET("general/support")
    Call<JsonObject> call_support();

    interface callSupportCallback {
        void onResponse(String phone);

        void onFailure(String error);
    }

    @PUT("driver/location")
    Call<Coordinate> updateUserLocation(@Field("latitude") String latitude,
                                        @Field("longitude") String longitude);

    interface UpdateLocationCallback {
        void onResponse();

        void onFailure(String error);
    }

    @PUT("driver/visibility")
    Call<JsonObject> setVisibility(@Field("visible") boolean visible,
                                   @Field("latitude") String latitude,
                                   @Field("longitude") String longitude);

    interface VisibilityCallback {
        void onResponse(String success);

        void onFailure(String error);
    }

}
