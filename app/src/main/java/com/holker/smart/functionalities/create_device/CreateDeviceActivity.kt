package com.holker.smart.functionalities.create_device

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.holker.smart.R
import com.holker.smart.databinding.ActivityCreateDeviceBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import javax.inject.Inject


class CreateDeviceActivity : AppCompatActivity(), Injectable {
    private val _TAG = CreateDeviceActivity::class.java.name

    private lateinit var _binding: ActivityCreateDeviceBinding
    private lateinit var _viewModel: CreateDeviceVM
    private lateinit var _sharedPref: SharedPreferences

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<CreateDeviceVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        //set up shared preference
        _sharedPref = applicationContext.getSharedPreferences(
            getString(R.string.preference_key),
            Context.MODE_PRIVATE
        )

        //Init token
        _viewModel.token = getToken()


    }

    fun initBinding() {
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_create_device)
        _viewModel =
            ViewModelProviders.of(this, viewModelInjectionFactory).get(CreateDeviceVM::class.java)
        _binding.viewModel = _viewModel
        _viewModel.event.observe(this, Observer { event ->
            when (event) {
                is CreateDeviceState.DeviceCreateSuccessful -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Your API KEY")
                    builder.setMessage(event.apiKey)
                    builder.setPositiveButton("Cancel") { _, _ ->
                        finish()
                    }
                    builder.create().show()
                }
            }
        })
    }

    private fun getToken(): String {
        return _sharedPref.getString("token", "")!!
    }
}