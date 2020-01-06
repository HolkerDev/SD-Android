package com.holker.smart.functionalities.main.fragments.devices

sealed class DeviceState {
    data class Error(var errorMessage: String) : DeviceState()
    object Wait : DeviceState()
}