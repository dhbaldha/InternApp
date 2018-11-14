package com.myinternapp.networking;

import com.myinternapp.networking.response.ResponseMovies;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppApi {
//        @POST("/login")
//        void loginUser(@Body LoginRequest loginRequest, Callback<LoginResponse> token);
//
//        @GET("/resetpassword/{username}")
//        void triggerResetPassword(@Path("username") String username, Callback<String> result);
//
//        @GET("/legal/{which}")
//        void getPrivacyAndTos(@Path("which") String which, Callback<PrivacyAndTos> privacyAndTos);

        @GET("json/movies.json")
        Call<ArrayList<ResponseMovies>> getGitHubData();
    }