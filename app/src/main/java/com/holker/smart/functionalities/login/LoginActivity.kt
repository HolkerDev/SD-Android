package com.holker.smart.functionalities.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.holker.smart.R
import com.holker.smart.databinding.ActivityLoginBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), Injectable {
    private val TAG = LoginActivity::class.java.name

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginVM

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<LoginVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, viewModelInjectionFactory).get(LoginVM::class.java)
        binding.viewModel = viewModel
    }
}