package com.example.myapplication.APIHelper;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("/shwift/listing")
    Call<ResponseBody> getJobList();
}
