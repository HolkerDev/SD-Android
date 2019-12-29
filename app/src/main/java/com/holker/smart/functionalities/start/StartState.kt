package com.holker.smart.functionalities.start

import com.holker.smart.data.model.UserDetailedInfo

sealed class StartState {
    object Wait : StartState()
    object Login : StartState()
    data class Error(var errorMessage: String) : StartState()
    data class TokenSuccess(var userDetails: UserDetailedInfo) : StartState()
}