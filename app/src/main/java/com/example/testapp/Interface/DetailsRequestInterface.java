package com.example.testapp.Interface;

import com.example.testapp.Model.UserDetails;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DetailsRequestInterface {

    String BASE_URL = "https://reqres.in/api/";
    String second = "users?page=1";

    @GET(second)
    Call<UserDetails> getJSON();
}
