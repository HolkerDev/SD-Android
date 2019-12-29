package com.holker.smart.functionalities.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.holker.smart.R
import com.holker.smart.databinding.ActivityStartBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import com.holker.smart.functionalities.login.LoginActivity
import javax.inject.Inject

class StartActivity : AppCompatActivity(), Injectable {
    private val TAG = StartActivity::class.java.name

    private lateinit var binding: ActivityStartBinding
    private lateinit var viewModel: StartVM

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<StartVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    override fun onStart() {
        super.onStart()

        val sharedPreference = applicationContext.getSharedPreferences(
            getString(R.string.preference_key), Context.MODE_PRIVATE
        )
        val token = sharedPreference.getString("token", "")
        viewModel.validateToken(token!!)
    }

    fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
        viewModel = ViewModelProviders.of(this, viewModelInjectionFactory).get(StartVM::class.java)
        binding.viewModel = viewModel
        viewModel.event.observe(this, Observer { event ->
            when (event) {
                StartState.Login -> {
                    val loginIntent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(loginIntent)
                    finish()
                }
            }
        })
    }
}
