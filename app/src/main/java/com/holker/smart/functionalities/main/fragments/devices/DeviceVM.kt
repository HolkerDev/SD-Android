package com.holker.smart.functionalities.main.fragments.devices

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.holker.smart.data.model.OwnDevice
import com.holker.smart.data.model.UserDetailedInfo
import com.holker.smart.data.repository.SmartAdApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DeviceVM @Inject constructor(val service: SmartAdApiService) : ViewModel() {

    private val _TAG = DeviceVM::class.java.name

    var event = MutableLiveData<DeviceState>()

    init {
        event.value = DeviceState.Wait
    }

    fun checkPermission(user: UserDetailedInfo): Boolean {
        Log.i(_TAG, "User details : isStaff == ${user.isStaff}")
        return user.isStaff
    }

    fun createNewDevice() {
        event.value = DeviceState.CreateNewDevice
    }

    fun pullDeviceList(token: String) {
        val callDeviceList = service.getOwnDevices("Token $token")

        callDeviceList.enqueue(object : Callback<List<OwnDevice>> {
            override fun onFailure(call: Call<List<OwnDevice>>, t: Throwable) {
                Log.e(_TAG, "Error while pulling devices list. Error : ${t.message}")
                event.value = DeviceState.Error("Error while retrieving devices list. Try later.")
            }

            override fun onResponse(
                call: Call<List<OwnDevice>>,
                response: Response<List<OwnDevice>>
            ) {
                when (response.code()) {
                    200 -> {
                        event.value = DeviceState.PullDevicesSuccessful(response.body()!!)
                    }
                    401 -> {
                        Log.e(_TAG, "Error status code: 401 while pulling device list")
                    }
                    else -> {
                        event.value = DeviceState.Error("Internal error. Contact with admin.")
                    }
                }
            }

        })
    }

}