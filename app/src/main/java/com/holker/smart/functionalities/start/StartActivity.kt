package com.holker.smart.functionalities.start

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
import com.holker.smart.databinding.ActivityStartBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import com.holker.smart.functionalities.main.MainActivity
import javax.inject.Inject

class StartActivity : AppCompatActivity(), Injectable {
    private val TAG = StartActivity::class.java.name

    private lateinit var binding: ActivityStartBinding
    private lateinit var viewModel: StartVM
    private lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<StartVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    override fun onStart() {
        super.onStart()

        sharedPref = applicationContext.getSharedPreferences(
            getString(R.string.preference_key), Context.MODE_PRIVATE
        )
        val token = sharedPref.getString("token", "")
        viewModel.validateToken(token!!)
    }

    fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
        viewModel = ViewModelProviders.of(this, viewModelInjectionFactory).get(StartVM::class.java)
        binding.viewModel = viewModel
        viewModel.event.observe(this, Observer { event ->
            when (event) {
                StartState.Login -> {
//                    val loginIntent = Intent(applicationContext, LoginActivity::class.java)
//                    startActivity(loginIntent)
//                    finish()

                    sharedPref.edit()
                        .putString("token", "09bba034634ca17a88a62e382156db544c598a32").apply()
                    Log.i(TAG, "Paste 09bba034634ca17a88a62e382156db544c598a32 to sharedPref")
                }
                is StartState.Error -> {
                    Toast.makeText(
                        applicationContext,
                        "Authorization error. Please try later.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is StartState.TokenSuccess -> {
                    //Put user details to sharedPref to use in future
                    val gson = Gson()
                    sharedPref.edit().putString("userDetails", gson.toJson(event.userDetails))
                        .apply()
                    val mainIntent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }
            }
        })
    }
}
