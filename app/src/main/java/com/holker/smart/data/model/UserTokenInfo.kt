package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName

data class UserTokenInfo(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String
)