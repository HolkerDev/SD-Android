package com.holker.smart.functionalities.create_device

sealed class CreateDeviceState {
    object Wait : CreateDeviceState()
    data class DeviceCreateSuccessful(var apiKey: String) : CreateDeviceState()
}