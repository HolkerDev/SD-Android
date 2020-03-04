package com.holker.smart.functionalities.main.fragments.advertising

sealed class AdvertisingState {
    object CreateAdvertising : AdvertisingState()
    data class Error(var errorMessage: String) : AdvertisingState()
}