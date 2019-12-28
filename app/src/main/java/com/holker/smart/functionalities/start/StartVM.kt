package com.holker.smart.functionalities.start

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class StartVM @Inject constructor() : ViewModel() {

    private val TAG = StartVM::class.java.name

    var event = MutableLiveData<StartState>()

    init {
        event.value = StartState.Wait
    }

    fun validateToken(token: String) {
        if (token == "") {
            Log.i(TAG, "Auth token is empty. Return Login signal.")
            event.value = StartState.Login
        } else {
            Log.i(TAG, "Auth token is not empty. Sending request to API.")
            // TODO : Send request and check token and return a details about user
        }
    }
}