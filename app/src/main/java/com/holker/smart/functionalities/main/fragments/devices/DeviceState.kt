package com.holker.smart.functionalities.main.fragments.devices

import com.holker.smart.data.model.OwnDevice

sealed class DeviceState {
    data class Error(var errorMessage: String) : DeviceState()
    object Wait : DeviceState()
    data class PullDevicesSuccessful(var deviceList: List<OwnDevice>) : DeviceState()
    object CreateNewDevice : DeviceState()
}