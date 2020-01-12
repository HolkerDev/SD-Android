package com.holker.smart.functionalities.main.fragments.advertising

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AdvertisingVM @Inject constructor() : ViewModel() {
    private val _TAG = AdvertisingVM::class.java

    var event = MutableLiveData<AdvertisingState>()

    fun createAdvertising() {
        event.value = AdvertisingState.CreateAdvertising
    }
}
