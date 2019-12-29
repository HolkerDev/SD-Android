package com.holker.smart.functionalities.sign_up

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.holker.smart.data.model.*
import com.holker.smart.data.repository.SmartAdApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SignUpVM @Inject constructor(var service: SmartAdApiService) : ViewModel() {

    private val _TAG = SignUpVM::class.java.name

    var event = MutableLiveData<SignUpState>()

    var email = ObservableField<String>("")
    var password = ObservableField<String>("")
    var passwordConfirm = ObservableField<String>("")
    var username = ObservableField<String>("")

    init {
        event.value = SignUpState.Wait
    }

    fun signUp() {
        validatePasswords()
        val createUserBody = CreateUserBody(
            email.get()!!,
            username.get()!!,
            password.get()!!
        )
        val callCreateUser = service.postCreateUser(createUserBody)
        callCreateUser.enqueue(object : Callback<UserCredentials> {
            override fun onFailure(call: Call<UserCredentials>, t: Throwable) {
                Log.e(_TAG, "Error while creating a new user. Error : ${t.message}")
            }

            override fun onResponse(
                call: Call<UserCredentials>,
                response: Response<UserCredentials>
            ) {
                when (response.code()) {
                    201 -> {
                        createToken(email.get()!!, password.get()!!, username.get()!!)
                    }
                    else -> {
                        Log.e(
                            _TAG,
                            "Unhandled response code while creating a user. Response ${response.code()}"
                        )
                    }
                }
            }
        })
    }

    fun createToken(email: String, password: String, username: String) {
        val userTokenInfo = UserTokenInfo(email, password)
        val callCreateToken = service.postCreateToken(userTokenInfo)
        callCreateToken.enqueue(object : Callback<Token> {
            override fun onFailure(call: Call<Token>, t: Throwable) {
                Log.e(_TAG, "Error while getting token. Error : ${t.message}")
            }

            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                when (response.code()) {
                    200 -> {
                        val token = response.body()?.token!!
                        getUserDetails(token, email, username)
                    }
                    else -> {
                        Log.e(
                            _TAG,
                            "Unhandled response code while getting token. Response ${response.code()}"
                        )
                    }
                }
            }

        })
    }

    fun getUserDetails(token: String, email: String, username: String) {
        val callUserDetails = service.getUserInfo("Token $token")
        callUserDetails.enqueue(object : Callback<UserDetailedInfo> {
            override fun onFailure(call: Call<UserDetailedInfo>, t: Throwable) {
                Log.e(_TAG, "Error while getting user details. Error : ${t.message}")
            }

            override fun onResponse(
                call: Call<UserDetailedInfo>,
                response: Response<UserDetailedInfo>
            ) {
                when (response.code()) {
                    200 -> {
                        event.value = SignUpState.SignUpSuccess(
                            token,
                            UserDetailedInfo(
                                email,
                                username,
                                response.body()?.isStaff!!
                            )
                        )
                    }
                    else -> {
                        Log.e(
                            _TAG,
                            "Unhandled response code while getting user details. Response ${response.code()}"
                        )
                    }
                }
            }

        })
    }

    private fun validatePasswords() {
        //TODO : Create validations

        // if password is not the same
        if (password.get()!! != passwordConfirm.get()!!) {
            event.value = SignUpState.Error("Passwords are not the same.")
            return
        }

        // if password is too short
        if (password.get()!!.length < 5) {
            event.value = SignUpState.Error("Password is too short. At least 5 chars.")
            return
        }
    }

}