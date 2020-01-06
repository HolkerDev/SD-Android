package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName

data class OwnDevice(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("key") var key: String,
    @SerializedName("is_active") var isActive: Boolean
)
