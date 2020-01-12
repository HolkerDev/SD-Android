package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName
import java.io.File

data class AdvertisingCreateInfo(
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: File,
    @SerializedName("seconds") val seconds: Int,
    @SerializedName("fromDate") val fromDate: String,
    @SerializedName("toDate") val toDate: String,
    @SerializedName("audiences") val audiences: List<String>,
    @SerializedName("devices") val devices: List<String>
)