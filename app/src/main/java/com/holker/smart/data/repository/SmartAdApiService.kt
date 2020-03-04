package com.holker.smart.data.repository

import com.holker.smart.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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
    fun postCreateDevice(@Header("Authorization") token: String, @Body name: DeviceNameRequest): Call<OwnDevice>

    @GET("/api/advertising/audiences")
    fun getAudiences(@Header("Authorization") token: String): Call<List<Audience>>

    @Multipart
    @POST("api/advertising/advertising/")
    fun postAdvertisingMultipart(
        @Header("Authorization") token: String,
        @PartMap partMap: MutableMap<String, RequestBody>,
        @Part image: MultipartBody.Part,
        @Part audiences: ArrayList<MultipartBody.Part>,
        @Part devices: ArrayList<MultipartBody.Part>
    ): Call<AdvertisingCreateResponse>

    @GET("api/advertising/devices-all")
    fun getAllDevices(@Header("Authorization") token: String): Call<List<ResponseDeviceAll>>

    @GET("api/advertising/advertising")
    fun getAdvertising(@Header("Authorization") token: String): Call<List<OwnAdvertising>>
}