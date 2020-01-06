package com.holker.smart.functionalities.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.holker.smart.R
import com.holker.smart.data.model.UserDetailedInfo
import com.holker.smart.databinding.ActivityMainBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import com.holker.smart.functionalities.main.fragments.advertising.AdvertisingFragment
import com.holker.smart.functionalities.main.fragments.devices.DeviceFragment
import com.holker.smart.functionalities.main.fragments.profile.ProfileFragment
import com.holker.smart.functionalities.main.fragments.statistic.StatisticFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), Injectable, HasSupportFragmentInjector {
    private val TAG = MainActivity::class.java.name

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainVM

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

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


        // Open advertising fragment first
        openFragment(AdvertisingFragment())


        // Bottom menu listener
        activity_main_bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_menu_advertising -> {
                    openFragment(AdvertisingFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.main_menu_devices -> {
                    openFragment(DeviceFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.main_menu_profile -> {
                    openFragment(ProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.main_menu_statistic -> {
                    openFragment(StatisticFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelInjectionFactory).get(MainVM::class.java)
        binding.viewModel = viewModel

        val gson = Gson()
        val json: String = _sharedPref.getString("userDetails", "").toString()
        val obj: UserDetailedInfo =
            gson.fromJson<UserDetailedInfo>(json, UserDetailedInfo::class.java)
    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}