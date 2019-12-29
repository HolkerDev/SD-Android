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
    private val TAG = LoginActivity::class.java.name

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginVM
    private lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<LoginVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = applicationContext.getSharedPreferences(
            getString(R.string.preference_key),
            Context.MODE_PRIVATE
        )
        initBinding()
    }

    fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, viewModelInjectionFactory).get(LoginVM::class.java)
        binding.viewModel = viewModel
        viewModel.event.observe(this, Observer { event ->
            when (event) {
                is LoginState.Error -> {
                    Toast.makeText(
                        applicationContext,
                        event.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is LoginState.LoginSuccess -> {
                    Log.i(TAG, "LoginSuccess. Saving data to shared preference.")
                    //Save token and userDetails
                    val gson = Gson()
                    sharedPref.edit().putString("token", event.token)
                        .putString("userDetails", gson.toJson(event.userDetails)).apply()

                    val mainIntent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }
                LoginState.SignUp -> {
                    Log.i(TAG, "SignUp. Switching to SignUp Activity.")
                    val signUpIntent = Intent(applicationContext, SignUpActivity::class.java)
                    startActivity(signUpIntent)
                }
                else -> {
                    Log.e(TAG, "Unexpected event.")
                }
            }
        })
    }
}