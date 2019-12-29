package com.holker.smart.functionalities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.holker.smart.R
import com.holker.smart.databinding.ActivityMainBinding
import com.holker.smart.di.ViewModelInjectionFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.name

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainVM

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<MainVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelInjectionFactory).get(MainVM::class.java)
        binding.viewModel = viewModel
    }
}