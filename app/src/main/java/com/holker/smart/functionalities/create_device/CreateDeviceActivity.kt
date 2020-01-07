package com.holker.smart.functionalities.create_device

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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
    }

    fun initBinding() {
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_create_device)
        _viewModel =
            ViewModelProviders.of(this, viewModelInjectionFactory).get(CreateDeviceVM::class.java)
        _binding.viewModel = _viewModel
    }
}