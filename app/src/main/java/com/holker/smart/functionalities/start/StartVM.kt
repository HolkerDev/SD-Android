package com.holker.smart.functionalities.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class StartVM @Inject constructor() : ViewModel() {

    var event = MutableLiveData<StartState>()

    init {
        event.value = StartState.Wait
    }

    fun validateToken(token: String) {
        if (token == "") {
            event.value = StartState.Login
        } else {
            // TODO : Send request and check token and return a details about user
        }
    }
}