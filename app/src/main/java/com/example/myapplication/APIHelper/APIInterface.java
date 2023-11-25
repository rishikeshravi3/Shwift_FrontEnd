package com.example.myapplication.APIHelper;

import com.example.myapplication.SignUpModel;
import com.example.myapplication.SignUpResponseModel;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface

APIInterface {

    @GET("/shwift/listing")
    Call<ResponseBody> getJobList();

    @POST("/shwift/signUp")
    Call<SignUpModel> createPost(@Body SignUpModel signUpModel);
    Call<ResponseBody> postSignup();
}
