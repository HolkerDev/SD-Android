package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName

data class OwnAdvertising(
    @SerializedName("name") var name: String,
    @SerializedName("devices") var devices: List<OwnDevice>,
    @SerializedName("audiences") var audiences: List<Audience>
)