package com.holker.smart.functionalities.main.fragments.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.holker.smart.R
import com.holker.smart.data.model.UserDetailedInfo
import com.holker.smart.databinding.FragmentProfileBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import com.holker.smart.functionalities.login.LoginActivity
import javax.inject.Inject

class ProfileFragment : Fragment(), Injectable {
    private val _TAG = ProfileFragment::class.java.name
    private lateinit var _viewModel: ProfileVM
    private lateinit var _binding: FragmentProfileBinding

    private lateinit var _sharedPref: SharedPreferences

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<ProfileVM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        _viewModel = ViewModelProviders.of(this, viewModelInjectionFactory)
            .get(ProfileVM::class.java)
        _binding.viewModel = _viewModel
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _sharedPref = activity?.applicationContext!!.getSharedPreferences(
            getString(R.string.preference_key),
            Context.MODE_PRIVATE
        )

        _viewModel.loadProfile(getUserObject())

        initBinding()
    }

    private fun initBinding() {
        _viewModel.event.observe(this, Observer { event ->
            when (event) {
                ProfileState.LogOut -> {
                    _sharedPref.edit().clear().apply()
                    val intentLogin = Intent(context, LoginActivity::class.java)
                    startActivity(intentLogin)
                    activity?.supportFragmentManager?.popBackStack()
                }
            }
        })
    }


    private fun getUserObject(): UserDetailedInfo {
        val gson = Gson()
        val json: String = _sharedPref.getString("userDetails", "").toString()
        val userInfo = gson.fromJson(json, UserDetailedInfo::class.java)
        Log.i(
            _TAG,
            "User was pulled from shared pref : staff : ${userInfo.isStaff}, name : ${userInfo.name},email :  ${userInfo.email}"
        )
        return userInfo
    }
}