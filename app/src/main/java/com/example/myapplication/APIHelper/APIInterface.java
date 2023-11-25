package com.example.myapplication.APIHelper;

import com.example.myapplication.JobListing.JobListinRequest;
import com.example.myapplication.JobListing.SaveJobModel;
import com.example.myapplication.LoginModel;
import com.example.myapplication.SignUpModel;
import com.example.myapplication.SignUpResponseModel;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("/shwift/fetchSpecificListing")
    Call<ResponseBody> getJobList(@Body JobListinRequest input);

    @POST("/shwift/signUp")
    Call<SignUpModel> createPost(@Body SignUpModel signUpModel);

    @GET("shwift/login/{userName}")
    Call<LoginModel> login(
            @Path("userName") String userName,
            @Query("password") String password
    );

    @POST("/shwift/saveJob")
    Call<ResponseBody> saveJob(@Body SaveJobModel saveJobModel);

    @POST("/shwift/deleteSavedJob")
    Call<ResponseBody> deleteSavedJob(@Body SaveJobModel saveJobModel);
}
