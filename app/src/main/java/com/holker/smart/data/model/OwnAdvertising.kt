package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName

data class OwnAdvertising(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("fromDate") var fromDate: String,
    @SerializedName("toDate") var toDate: String
)