package com.holker.smart.functionalities.create_advertising

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.holker.smart.data.model.AdvertisingCreateResponse
import com.holker.smart.data.model.Audience
import com.holker.smart.data.model.ResponseDeviceAll
import com.holker.smart.data.repository.SmartAdApiService
import com.holker.smart.functionalities.create_advertising.models.AudienceSelect
import com.holker.smart.functionalities.create_advertising.models.DeviceSelect
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject


class CreateAdvertisingVM @Inject constructor(val service: SmartAdApiService) : ViewModel() {

    private val _TAG = CreateAdvertisingVM::class.java.name

    var event = MutableLiveData<CreateAdvertisingState>()

    lateinit var imageFile: File
    lateinit var imageUri: Uri
    lateinit var token: String
    lateinit var imagePartData: MultipartBody.Part

    var nameObservable = ObservableField<String>()
    var secondsObservable = ObservableField<String>()
    var startDateObservable = ObservableField<String>("2020-01-10")
    var endDateObservable = ObservableField<String>("2020-01-15")
    var startTimeObservable = ObservableField<String>("19:35")
    var endTimeObservable = ObservableField<String>("19:35")

    var audienceList = MutableLiveData<ArrayList<AudienceSelect>>()
    var devicesList = MutableLiveData<ArrayList<DeviceSelect>>()

    init {
        audienceList.value = arrayListOf()
    }

    fun uploadImage() {
        event.value = CreateAdvertisingState.UploadImage
    }

    fun pullAudienceSelectList() {
        val getAudienceSelect = service.getAudiences("Token $token")
        getAudienceSelect.enqueue(object : Callback<List<Audience>> {
            override fun onFailure(call: Call<List<Audience>>, t: Throwable) {
                Log.e(_TAG, "Error while pulling audiences. Error : ${t.message}")
            }

            override fun onResponse(
                call: Call<List<Audience>>,
                response: Response<List<Audience>>
            ) {
                when (response.code()) {
                    200 -> {
                        val audiences: List<Audience> = response.body()!!
                        val audienceToAdapter = arrayListOf<AudienceSelect>()
                        for (audience in audiences) {
                            Log.i(_TAG, "Add audience to list")
                            audienceToAdapter.add(
                                AudienceSelect(
                                    audience.name,
                                    audience.id.toString()
                                )
                            )
                        }
                        audienceList.value = audienceToAdapter
                    }
                    else -> {
                        Log.e(_TAG, "Wrong code while pulling audiences. Code : ${response.code()}")
                    }
                }
            }
        })
    }

    fun pullDeviceSelectList() {
        val getAllDevices = service.getAllDevices("Token $token")
        getAllDevices.enqueue(object : Callback<List<ResponseDeviceAll>> {
            override fun onFailure(call: Call<List<ResponseDeviceAll>>, t: Throwable) {
                Log.e(_TAG, "Error while pulling devices. Error : ${t.message}")
            }

            override fun onResponse(
                call: Call<List<ResponseDeviceAll>>,
                response: Response<List<ResponseDeviceAll>>
            ) {
                when (response.code()) {
                    200 -> {
                        val prepareList = arrayListOf<DeviceSelect>()
                        for (device in response.body()!!) {
                            prepareList.add(DeviceSelect(device.name, device.id))
                        }
                        devicesList.value = prepareList
                    }
                    else -> {
                        Log.e(
                            _TAG,
                            "Unhandled status code while pulling devices. Code : ${response.code()}"
                        )
                        event.value =
                            CreateAdvertisingState.Error("Server error. Please try later.")
                    }
                }
            }

        })
    }

    fun submitAdvertising() {
        if (!checkImage()) {
            event.value = CreateAdvertisingState.Error("Image was not selected")
        } else {
//            val name = nameObservable.get()!!
//            val seconds = secondsObservable.get()!!.toInt()
//            val startDate = parseDate(startDateObservable.get()!!, startTimeObservable.get()!!)
//            val endDate = parseDate(endDateObservable.get()!!, endTimeObservable.get()!!)
            val devices: ArrayList<MultipartBody.Part> = arrayListOf()
            val audiences: ArrayList<MultipartBody.Part> = arrayListOf()

            for (device in devicesList.value!!) {
                devices.add(MultipartBody.Part.createFormData("devices", device.id))
            }

            for (audience in audienceList.value!!) {
                audiences.add(MultipartBody.Part.createFormData("audiences", audience.id))
            }

            val map = hashMapOf<String, RequestBody>()
            map["name"] = createPartFromString(nameObservable.get()!!)
            map["seconds"] = createPartFromString(secondsObservable.get()!!)
            map["fromDate"] = createPartFromString(
                parseDate(
                    startDateObservable.get()!!,
                    startTimeObservable.get()!!
                )
            )
            map["toDate"] = createPartFromString(
                parseDate(
                    endDateObservable.get()!!,
                    endTimeObservable.get()!!
                )
            )

//            List<MultipartBody.Part> descriptionList = new ArrayList<>();
//            descriptionList.add(MultipartBody.Part.createFormData("param_name_here", values));

            val postAdvertising = service.postAdvertisingMultipart(
                "Token $token",
                map,
                imagePartData,
                audiences,
                devices
            )
            postAdvertising.enqueue(object : Callback<AdvertisingCreateResponse> {
                override fun onFailure(call: Call<AdvertisingCreateResponse>, t: Throwable) {
                    Log.e(_TAG, "Error while creating Advertising. Error : ${t.message}")
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
        Log.i(_TAG, stringTime)
        val timeParts = stringTime.split(":")
        Log.i(_TAG, "Time parts : ${timeParts}")
        val hours = timeParts[0]
        val minutes = timeParts[1]
        // example 2020-01-15T19:35:21.572483Z
        return "$year-$month-${day}T$hours:$minutes:00.000000"
    }

    private fun createPartFromString(string: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, string)
    }
}