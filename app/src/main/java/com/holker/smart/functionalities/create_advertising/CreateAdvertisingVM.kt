package com.holker.smart.functionalities.create_advertising

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import javax.inject.Inject

class CreateAdvertisingVM @Inject constructor() : ViewModel() {

    var event = MutableLiveData<CreateAdvertisingState>()

    lateinit var imageFile: File

    var nameObservable = ObservableField<String>("")
    var secondsObservable = ObservableField<String>("")
    var startDateObservable = ObservableField<String>("")
    var endDateObservable = ObservableField<String>("")
    var startTimeObservable = ObservableField<String>("")
    var endTimeObservable = ObservableField<String>("")

    fun uploadImage() {
        event.value = CreateAdvertisingState.UploadImage
    }

    fun setImage(imageFile: File) {
        this.imageFile = imageFile
    }

    fun submitAdvertising() {
        if (!checkImage()) {
            event.value = CreateAdvertisingState.Error("Image was not selected")
        } else {
            val name = nameObservable.get()!!
            val seconds = secondsObservable.get()!!
            val startDate = parseDate(startDateObservable.get()!!, startTimeObservable.get()!!)
            val endDate = parseDate(endDateObservable.get()!!, endDateObservable.get()!!)

        }
    }

    fun checkImage(): Boolean {
        return imageFile.exists()
    }

    fun parseDate(stringDate: String, stringTime: String): String {
        val dateParts = stringDate.split("-")
        val year = dateParts[0]
        val month = dateParts[1]
        val day = dateParts[2]
        val timeParts = stringTime.split(":")
        val hours = timeParts[0]
        val minutes = timeParts[1]
        // example 2020-01-15T19:35:21.572483Z
        return "$year-$month-${day}T$hours:$minutes:00.000000Z"
    }
}