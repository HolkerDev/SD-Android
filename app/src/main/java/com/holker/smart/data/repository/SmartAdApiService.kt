package com.holker.smart.data.repository

import com.holker.smart.data.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

public interface SmartAdApiService {
    @POST("/api/user/create/")
    fun postCreateUser(@Body userCreate: CreateUserBody): Call<UserCredentials>

    @POST("/api/user/token/")
    fun postCreateToken(@Body userCredentials: UserTokenInfo): Call<Token>

    @GET("/api/user/me/")
    fun getUserInfo(@Header("Authorization") token: String): Call<UserDetailedInfo>

    @GET("/api/advertising/devices/")
    fun getOwnDevices(@Header("Authorization") token: String): Call<List<OwnDevice>>

    @POST("/api/advertising/devices/")
    fun postCreateDevice(@Header("Authorization") token: String, @Body name: String): Call<OwnDevice>
}