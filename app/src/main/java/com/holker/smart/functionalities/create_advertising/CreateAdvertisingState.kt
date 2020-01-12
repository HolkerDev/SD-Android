package com.holker.smart.functionalities.create_advertising

sealed class CreateAdvertisingState {
    object UploadImage : CreateAdvertisingState()
    data class Error(var errorMessage: String) : CreateAdvertisingState()
    object CreatedSuccessful : CreateAdvertisingState()
}