package com.jabari.driver.network.config;

import com.google.gson.JsonObject;
import com.jabari.driver.network.model.Accounting;
import com.jabari.driver.network.model.Coordinate;
import com.jabari.driver.network.model.History;
import com.jabari.driver.network.model.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("auth/driver/login/")
    Call<JsonObject> getVerifyCode(@Field("mobile") String PhoneNumber);

    interface UserVerifyCodeCallback {
        void onResponse(String success);

        void onFailure(String error);
    }

    @FormUrlEncoded
    @POST("auth/driver/loginverify")
    Call<JsonObject> check_login(@Field("mobile") String PhoneNumber,
                                 @Field("verifyCode") String verifyCode);

    interface LoginUserCallback {
        void onResponse(User user, String token);

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
                             @Field("shebaNumber") String shebaNumber,
                             @Field("vehicle") String vehicle,
                             @Field("notifyId") String notifyId);

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

    @FormUrlEncoded
    @PUT("driver/location")
    Call<Coordinate> updateUserLocation(@Field("latitude") String latitude,
                                        @Field("longitude") String longitude);

    interface UpdateLocationCallback {
        void onResponse();

        void onFailure(String error);
    }

    @FormUrlEncoded
    @PUT("driver/visibility")
    Call<JsonObject> setVisibility(@Field("visible") boolean visible,
                                   @Field("latitude") String latitude,
                                   @Field("longitude") String longitude);

    interface VisibilityCallback {
        void onResponse(String success);

        void onFailure(String error);
    }

    @GET("general/laws")
    Call<JsonObject> get_rules();

    interface GetLawsCallback {
        void onResponse(String laws);

        void onFailure(String error);
    }

    @GET("driver/accounting")
    Call<Accounting> accounting();

    interface AccountingCallback {
        void onResponse(Accounting accounting);

        void onFailure(String error);
    }

    @GET("driver/accounting")
    Call<JsonObject> history();

    interface HistoryCallback {
        void onResponse(ArrayList<History> historyList);

        void onFailure(String error);
    }

    @GET("driver/star")
    Call<JsonObject> star();

    interface StarCallback {
        void onResponse(String starCount, String tripCount, String currentStar);

        void onFailure(String error);
    }

    @GET("driver/request/ready")
    Call<JsonObject> readyRequests();

    interface RequestCallback {
        void onResponse();

        void onFailure(String error);

    }

    @FormUrlEncoded
    @PUT("driver/request/ready")
    Call<JsonObject> acceptedRequest();

    interface AcceptedRequestCallback {
        void onResponse();

        void onFailure(String error);

    }

    @FormUrlEncoded
    @PUT("driver")
    Call<JsonObject> updateDriver(@Field("email") String email,
                                  @Field("name") String name,
                                  @Field("gender") String gender,
                                  @Field("mobile") String mobile,
                                  @Field("shebaNumber") String sheba,
                                  @Field("notifyId") String notifyId,
                                  @Field("documentMeli") String documentMeli,
                                  @Field("documentId") String documentId,
                                  @Field("documentLicense") String documentLicense,
                                  @Field("documentMilitary") String documentMilitary,
                                  @Field("documentGreenPaper") String documentGreenPaper,
                                  @Field("documentWaterBill") String documentWaterBill,
                                  @Field("documentElectricalBill") String documentElectricalBill);

    interface UpdateDriverCallback {
        void onResponse();

        void onFailure(String error);

    }

    @POST("driver/request")
    Call<JsonObject> detailHistory();

    interface DetailCallback {
        void onResponse();

        void onFailure(String error);
    }

    @Multipart
    @POST("image")
    Call<JsonObject> uploadPhotos(
            @Part MultipartBody.Part image,
            @Header("Authorization") String token
    );

    interface UploadFileCallback {
        void onResponse(String url);

        void onFailure(String error);

    }

}
