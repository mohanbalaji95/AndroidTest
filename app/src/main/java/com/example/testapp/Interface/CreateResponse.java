package com.example.testapp.Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CreateResponse {

    @FormUrlEncoded
    @POST("users")
    Call<ResponseBody> sendUser(
            @Field("name") String name,
            @Field("job") String job
    );
}
