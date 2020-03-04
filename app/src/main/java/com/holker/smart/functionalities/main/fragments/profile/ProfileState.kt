package com.holker.smart.functionalities.main.fragments.profile

sealed class ProfileState {
    data class Error(val message: String) : ProfileState()
    object Wait : ProfileState()
    object LogOut : ProfileState()
}