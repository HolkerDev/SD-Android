package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName

data class ResponseDeviceAll(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String
)