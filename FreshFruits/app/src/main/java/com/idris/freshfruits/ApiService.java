package com.idris.freshfruits;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("create-user.php")
    Call<ApiResponse> registerUser(
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<ApiResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );
    @GET("categories.php")
    Call<List<Category>> getCategories();
}