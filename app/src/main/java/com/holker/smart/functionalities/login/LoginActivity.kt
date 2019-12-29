package com.holker.smart.functionalities.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.holker.smart.R
import com.holker.smart.databinding.ActivityLoginBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import com.holker.smart.functionalities.main.MainActivity
import com.holker.smart.functionalities.sign_up.SignUpActivity
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), Injectable {
    private val _TAG = LoginActivity::class.java.name

    private lateinit var _binding: ActivityLoginBinding
    private lateinit var _viewModel: LoginVM
    private lateinit var _sharedPref: SharedPreferences

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<LoginVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _sharedPref = applicationContext.getSharedPreferences(
            getString(R.string.preference_key),
            Context.MODE_PRIVATE
        )
        initBinding()
    }

    fun initBinding() {
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        _viewModel = ViewModelProviders.of(this, viewModelInjectionFactory).get(LoginVM::class.java)
        _binding.viewModel = _viewModel
        _viewModel.event.observe(this, Observer { event ->
            when (event) {
                is LoginState.Error -> {
                    Toast.makeText(
                        applicationContext,
                        event.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is LoginState.LoginSuccess -> {
                    Log.i(_TAG, "LoginSuccess. Saving data to shared preference.")
                    //Save token and userDetails
                    val gson = Gson()
                    _sharedPref.edit().putString("token", event.token)
                        .putString("userDetails", gson.toJson(event.userDetails)).apply()

                    val mainIntent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }
                LoginState.SignUp -> {
                    Log.i(_TAG, "SignUp. Switching to SignUp Activity.")
                    val signUpIntent = Intent(applicationContext, SignUpActivity::class.java)
                    startActivity(signUpIntent)
                }
                else -> {
                    Log.e(_TAG, "Unexpected event.")
                }
            }
        })
    }
}