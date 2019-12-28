package com.holker.smart.functionalities.start

sealed class StartState {
    object Wait : StartState()
    object Login : StartState()
}