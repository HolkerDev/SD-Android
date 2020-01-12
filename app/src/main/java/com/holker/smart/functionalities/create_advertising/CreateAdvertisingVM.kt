package com.holker.smart.functionalities.create_advertising

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.holker.smart.data.model.AdvertisingCreateInfo
import com.holker.smart.data.model.AdvertisingCreateResponse
import com.holker.smart.data.repository.SmartAdApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class CreateAdvertisingVM @Inject constructor(val service: SmartAdApiService) : ViewModel() {

    private val _TAG = CreateAdvertisingVM::class.java.name

    var event = MutableLiveData<CreateAdvertisingState>()

    lateinit var imageFile: File
    lateinit var token: String

    var nameObservable = ObservableField<String>("testName")
    var secondsObservable = ObservableField<String>("15")
    var startDateObservable = ObservableField<String>("2020-01-10")
    var endDateObservable = ObservableField<String>("2020-01-15")
    var startTimeObservable = ObservableField<String>("19:35")
    var endTimeObservable = ObservableField<String>("19:35")

    fun uploadImage() {
        event.value = CreateAdvertisingState.UploadImage
    }


    fun submitAdvertising() {
        if (!checkImage()) {
            event.value = CreateAdvertisingState.Error("Image was not selected")
        } else {
            val name = nameObservable.get()!!
            val seconds = secondsObservable.get()!!.toInt()
            val startDate = parseDate(startDateObservable.get()!!, startTimeObservable.get()!!)
            val endDate = parseDate(endDateObservable.get()!!, endDateObservable.get()!!)
            val devices = arrayListOf<String>()
            val audiences = arrayListOf<String>()
            val postAdvertising = service.postAdvertising(
                "Token $token", AdvertisingCreateInfo(
                    name, imageFile, seconds, startDate, endDate, audiences, devices
                )
            )
            postAdvertising.enqueue(object : Callback<AdvertisingCreateResponse> {
                override fun onFailure(call: Call<AdvertisingCreateResponse>, t: Throwable) {
                    Log.e(_TAG, "Error while ")
                }

                override fun onResponse(
                    call: Call<AdvertisingCreateResponse>,
                    response: Response<AdvertisingCreateResponse>
                ) {
                    when (response.code()) {
                        201 -> {
                            Log.i(_TAG, "Advertising was created successfully")
                            event.value = CreateAdvertisingState.CreatedSuccessful
                        }
                        else -> {
                            Log.e(_TAG, "Unhandled response error : ${response.code()}")
                            event.value =
                                CreateAdvertisingState.Error("Server error. Please try again later.")
                        }
                    }
                }

            })
        }
    }

    private fun checkImage(): Boolean {
        return imageFile.exists()
    }

    private fun parseDate(stringDate: String, stringTime: String): String {
        val dateParts = stringDate.split("-")
        val year = dateParts[0]
        val month = dateParts[1]
        val day = dateParts[2]
        val timeParts = stringTime.split(":")
        val hours = timeParts[0]
        val minutes = timeParts[1]
        // example 2020-01-15T19:35:21.572483Z
        return "$year-$month-${day}T$hours:$minutes:00.000000"
    }
}