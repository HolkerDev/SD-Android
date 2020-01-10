package com.holker.smart.functionalities.create_advertising

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.holker.smart.R
import com.holker.smart.databinding.ActivityCreateAdvertisingBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import javax.inject.Inject

class CreateAdvertisingActivity : AppCompatActivity(), Injectable {
    private val _TAG = CreateAdvertisingActivity::class.java.name

    private lateinit var _binding: ActivityCreateAdvertisingBinding
    private lateinit var _viewModel: CreateAdvertisingVM
    private lateinit var _sharedPref: SharedPreferences

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<CreateAdvertisingVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _sharedPref = applicationContext.getSharedPreferences(
            getString(R.string.preference_key),
            Context.MODE_PRIVATE
        )
        initBinding()
    }

    fun initBinding() {
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_create_advertising)
        _viewModel =
            ViewModelProviders.of(this, viewModelInjectionFactory).get(CreateAdvertisingVM::class.java)
        _binding.viewModel = _viewModel
    }
}