package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName

data class Audience(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String
)