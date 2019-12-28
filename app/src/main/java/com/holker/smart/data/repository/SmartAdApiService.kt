package com.holker.smart.data.repository

import com.holker.smart.data.model.CreateUserBody
import com.holker.smart.data.model.Token
import com.holker.smart.data.model.UserCredentials
import com.holker.smart.data.model.UserDetailedInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

public interface SmartAdApiService {
    @POST("user/create/")
    fun postCreateUser(@Body userCreate: CreateUserBody): Call<UserCredentials>

    @POST("user/token/")
    fun postCreateToken(@Body userCredentials: UserCredentials): Call<Token>

    @GET("user/me/")
    fun getUserInfo(): Call<UserDetailedInfo>
}