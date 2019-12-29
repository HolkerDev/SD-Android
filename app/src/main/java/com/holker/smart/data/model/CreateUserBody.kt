package com.holker.smart.data.model

import com.google.gson.annotations.SerializedName

data class CreateUserBody(
    @SerializedName("email") var email: String,
    @SerializedName("name") var name: String,
    @SerializedName("password") var password: String
)