package com.campbellapps.christiancampbell.peoplemonv1.Network;

import com.campbellapps.christiancampbell.peoplemonv1.Models.Auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by christiancampbell on 11/7/16.
 */

public interface ApiService {

@POST("/api/Account/Register")
    Call<Void> register(@Body Auth account);


    @FormUrlEncoded
    @POST("token")
    Call<Auth> login(@Field("username") String email, @Field("password") String password, @Field("grant_type") String grantType);

    @GET("/v1/User/Nearby")
    Call<Auth[]> getUsers(@Query("RadiusInMeters") Integer radius);

    @POST("/v1/User/CheckIn")
    Call<Void> checkIn(@Body Auth check);

    @POST("/v1/User/Catch")
    Call<Void> userCatch(@Body Auth caughtUser);

    @GET("/v1/User/Caught")
    Call<Auth[]> userCaught();

    @GET("/api/Account/UserInfo")
    Call<Auth> accountInfo();

    @POST("/api/Account/UserInfo")
    Call<Void> update(@Body Auth updateInfo);





//@POST("register")
//Call<Account> register(@Body Account user); // sends register in to server
}
