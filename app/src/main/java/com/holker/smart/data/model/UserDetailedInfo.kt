package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName

data class UserDetailedInfo(
    @SerializedName("email") var email: String,
    @SerializedName("name") var name: String,
    @SerializedName("is_staff") var isStaff: Boolean
)