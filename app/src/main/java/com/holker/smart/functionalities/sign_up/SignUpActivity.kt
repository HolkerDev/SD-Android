package com.holker.smart.functionalities.sign_up

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.holker.smart.R
import com.holker.smart.databinding.ActivitySignUpBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import javax.inject.Inject

class SignUpActivity : AppCompatActivity(), Injectable {
    private val TAG = SignUpActivity::class.java.name

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpVM

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<SignUpVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        viewModel = ViewModelProviders.of(this, viewModelInjectionFactory).get(SignUpVM::class.java)
        binding.viewModel = viewModel
    }
}