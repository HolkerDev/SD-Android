package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName

data class AdvertisingCreateResponse(
    @SerializedName("id") val id: String,
    @SerializedName("") val name: String
)