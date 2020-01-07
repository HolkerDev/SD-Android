package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName

data class DeviceNameRequest(
    @SerializedName("name") val name: String
)