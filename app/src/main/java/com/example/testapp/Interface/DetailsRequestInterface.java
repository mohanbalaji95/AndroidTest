package com.example.testapp.Interface;

import com.example.testapp.Model.UserDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DetailsRequestInterface {

    String BASE_URL = "https://reqres.in/api/";

    @GET
    Call<UserDetails> getJSON(@Url String url);
}
