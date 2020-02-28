package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class OwnAdvertising(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("fromDate") var fromDate: Date,
    @SerializedName("toDate") var toDate: Date
)