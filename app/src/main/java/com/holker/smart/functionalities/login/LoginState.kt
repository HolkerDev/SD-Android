package com.holker.smart.functionalities.login

import com.holker.smart.data.model.UserDetailedInfo

sealed class LoginState {
    data class Error(var errorMessage: String) : LoginState()
    object Wait : LoginState()
    data class LoginSuccess(var token: String, var userDetails: UserDetailedInfo) : LoginState()
}