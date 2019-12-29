package com.holker.smart.functionalities.sign_up

import com.holker.smart.data.model.UserDetailedInfo

sealed class SignUpState {
    object Wait : SignUpState()
    data class Error(var errorMessage: String) : SignUpState()
    data class SignUpSuccess(var token: String, var userDetails: UserDetailedInfo) : SignUpState()
}