package com.example.ashwani.lattice.Data;

import com.example.ashwani.lattice.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @GET("/user")
    Call<UserJson2pojoSchema> getAllUser();

    @POST("/signup")
    @FormUrlEncoded
    Call<UserJson2pojoSchema> saveUser(@Field("name") String name,
                                       @Field("address") String address,
                                       @Field("email") String email,
                                       @Field("phone_no") String phone,
                                       @Field("password")String password);
    @POST("/signup")
    @FormUrlEncoded
    Call<UserJson2pojoSchema> saveU(@Body User user);
}
