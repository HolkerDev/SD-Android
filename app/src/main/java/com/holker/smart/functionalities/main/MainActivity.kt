package com.holker.smart.functionalities.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.holker.smart.R
import com.holker.smart.data.model.UserDetailedInfo
import com.holker.smart.databinding.ActivityMainBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), Injectable {
    private val TAG = MainActivity::class.java.name

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainVM

    private lateinit var _sharedPref: SharedPreferences

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<MainVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _sharedPref = applicationContext.getSharedPreferences(
            getString(R.string.preference_key),
            Context.MODE_PRIVATE
        )

        initBinding()
    }

    fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelInjectionFactory).get(MainVM::class.java)
        binding.viewModel = viewModel

        val gson = Gson()
        val json: String = _sharedPref.getString("userDetails", "").toString()
        val obj: UserDetailedInfo =
            gson.fromJson<UserDetailedInfo>(json, UserDetailedInfo::class.java)
        username.setText(obj.name)
        email.text = obj.email
        is_staff.text = obj.isStaff.toString()
    }
}