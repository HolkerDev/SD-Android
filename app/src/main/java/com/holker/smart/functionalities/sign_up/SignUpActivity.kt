package com.holker.smart.functionalities.sign_up

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.holker.smart.R
import com.holker.smart.databinding.ActivitySignUpBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import com.holker.smart.functionalities.start.StartActivity
import javax.inject.Inject

class SignUpActivity : AppCompatActivity(), Injectable {
    private val _TAG = SignUpActivity::class.java.name

    private lateinit var _binding: ActivitySignUpBinding
    private lateinit var _viewModel: SignUpVM

    private lateinit var _sharedPref: SharedPreferences

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<SignUpVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _sharedPref = applicationContext.getSharedPreferences(
            getString(R.string.preference_key),
            Context.MODE_PRIVATE
        )
        initBinding()
    }

    fun initBinding() {
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        _viewModel =
            ViewModelProviders.of(this, viewModelInjectionFactory).get(SignUpVM::class.java)
        _binding.viewModel = _viewModel
        _viewModel.event.observe(this, Observer { event ->
            when (event) {
                is SignUpState.Error -> {
                    Toast.makeText(applicationContext, event.errorMessage, Toast.LENGTH_SHORT)
                        .show()
                }
                is SignUpState.SignUpSuccess -> {
                    val token = event.token
                    val gson = Gson()
                    _sharedPref.edit().putString("token", token)
                        .putString("userDetails", gson.toJson(event.userDetails)).apply()
                    val startIntent = Intent(applicationContext, StartActivity::class.java)
                    startActivity(startIntent)
                    finish()
                }
            }
        })
    }
}