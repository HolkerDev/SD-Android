package com.holker.smart.functionalities.create_device

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.holker.smart.data.model.DeviceNameRequest
import com.holker.smart.data.model.OwnDevice
import com.holker.smart.data.repository.SmartAdApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CreateDeviceVM @Inject constructor(val service: SmartAdApiService) : ViewModel() {

    private val _TAG = CreateDeviceVM::class.java.name
    var token = ""

    var event = MutableLiveData<CreateDeviceState>()
    var nameObservable = ObservableField<String>("")

    init {
        event.value = CreateDeviceState.Wait
    }

    fun submitDevice() {
        Log.i(_TAG, "Name observable : ${nameObservable.get()!!}")
        val postCreateDevice =
            service.postCreateDevice(
                token = "Token $token",
                name = DeviceNameRequest(nameObservable.get()!!)
            )
        postCreateDevice.enqueue(object : Callback<OwnDevice> {
            override fun onFailure(call: Call<OwnDevice>, t: Throwable) {
                Log.e(_TAG, "Error while creating a new device. Error : ${t.message}")
            }

            override fun onResponse(call: Call<OwnDevice>, response: Response<OwnDevice>) {
                when (response.code()) {
                    201 -> {
                        Log.i(_TAG, "Device was created")
                        event.value =
                            CreateDeviceState.DeviceCreateSuccessful(response.body()?.key!!)
                    }
                    200 -> {
                        Log.i(_TAG, "Got 200. Idk")
                    }
                    400 -> {
                        Log.e(_TAG, "Error 400")
                    }
                    else -> {
                        Log.e(_TAG, "Unhandled status code. Status : ${response.code()}")
                    }
                }
            }

        })
    }
}