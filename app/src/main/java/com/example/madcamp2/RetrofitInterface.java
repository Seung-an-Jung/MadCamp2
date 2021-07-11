package com.example.madcamp2;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {

    @POST("/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/signup")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @POST("/addgroup")
    Call<Void> executeGroupAdd (@Body HashMap<String, String> map);

    @GET("/getgroup")
    Call<ArrayList<Listgroup>> executeGroupGet();

    @POST("/upload")
    Call<RequestBody> uplaodImage (@Part MultipartBody.Part part, @Part("somedate") RequestBody requestBody);


}