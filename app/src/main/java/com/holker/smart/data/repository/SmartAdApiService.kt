package com.holker.smart.data.repository

import com.holker.smart.data.model.CreateUserBody
import com.holker.smart.data.model.Token
import com.holker.smart.data.model.UserCredentials
import com.holker.smart.data.model.UserDetailedInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

public interface SmartAdApiService {
    @POST("/api/user/create/")
    fun postCreateUser(@Body userCreate: CreateUserBody): Call<UserCredentials>

    @POST("/api/user/token/")
    fun postCreateToken(@Body userCredentials: UserCredentials): Call<Token>

    @GET("/api/user/me/")
    fun getUserInfo(@Header("Authorization") token: String): Call<UserDetailedInfo>
}