package com.holker.smart.functionalities.main.fragments.advertising

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.holker.smart.data.model.OwnAdvertising
import com.holker.smart.data.repository.SmartAdApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AdvertisingVM @Inject constructor(val service: SmartAdApiService) : ViewModel() {
    private val _TAG = AdvertisingVM::class.java.name

    var event = MutableLiveData<AdvertisingState>()
    var advertisingList = MutableLiveData<ArrayList<OwnAdvertising>>()

    lateinit var token: String

    fun createAdvertising() {
        event.value = AdvertisingState.CreateAdvertising
    }

    fun pullAdvertising() {
        val callOwnAdvertising = service.getAdvertising("Token $token")
        callOwnAdvertising.enqueue(object : Callback<List<OwnAdvertising>> {
            override fun onFailure(call: Call<List<OwnAdvertising>>, t: Throwable) {
                Log.e(_TAG, "Error while pulling advertising list. Error : ${t.message}")
                event.value = AdvertisingState.Error("Server error. Please try again later.")
            }

            override fun onResponse(
                call: Call<List<OwnAdvertising>>,
                response: Response<List<OwnAdvertising>>
            ) {
                when (response.code()) {
                    200 -> {
                        val advertisingListToAdapter = arrayListOf<OwnAdvertising>()
                        for (advertising in response.body()!!) {
                            advertisingListToAdapter.add(
                                OwnAdvertising(
                                    advertising.id,
                                    advertising.name,
                                    advertising.fromDate,
                                    advertising.toDate
                                )
                            )
                        }
                        advertisingList.value = advertisingListToAdapter
                    }
                    else -> {
                        Log.e(_TAG, "Unhandled response code. Code : ${response.code()}")
                    }
                }
            }

        })
    }
}
