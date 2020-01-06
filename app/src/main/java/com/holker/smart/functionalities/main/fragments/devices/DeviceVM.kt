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
        return user.isStaff
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
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

}