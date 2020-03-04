package com.holker.smart.functionalities.main.fragments.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.holker.smart.data.model.UserDetailedInfo
import javax.inject.Inject

class ProfileVM @Inject constructor() : ViewModel() {

    private val _TAG = ProfileVM::class.java.simpleName

    var nameObservable = ObservableField<String>("")
    var emailObservable = ObservableField<String>("")
    var subscriptionObservable = ObservableField<String>("")

    var event = MutableLiveData<ProfileState>()

    init {
        event.value = ProfileState.Wait
    }

    fun loadProfile(userObject: UserDetailedInfo) {
        nameObservable.set(userObject.name)
        emailObservable.set(userObject.email)
        if (userObject.isStaff) {
            subscriptionObservable.set("active")
        } else {
            subscriptionObservable.set("canceled")
        }
    }

    fun logOut() {
        event.value = ProfileState.LogOut
    }
}