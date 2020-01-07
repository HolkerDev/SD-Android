package com.holker.smart.functionalities.main.fragments.devices

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.holker.smart.R
import com.holker.smart.data.model.UserDetailedInfo
import com.holker.smart.databinding.FragmentDeviceBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import com.holker.smart.functionalities.create_device.CreateDeviceActivity
import kotlinx.android.synthetic.main.fragment_device.*
import javax.inject.Inject

class DeviceFragment : Fragment(), Injectable {
    private val _TAG = DeviceFragment::class.java.name
    private lateinit var _viewModel: DeviceVM
    private lateinit var _binding: FragmentDeviceBinding

    private lateinit var _adapter: DeviceListAdapter

    private lateinit var _sharedPref: SharedPreferences

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<DeviceVM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_device, container, false)
        _viewModel = ViewModelProviders.of(this, viewModelInjectionFactory)
            .get(DeviceVM::class.java)
        _binding.viewModel = _viewModel
        initBindings()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set up shared preference
        _sharedPref = activity?.applicationContext!!.getSharedPreferences(
            getString(R.string.preference_key),
            Context.MODE_PRIVATE
        )

        //set up adapter
        _adapter = DeviceListAdapter(listOf())
        fragment_device_rv_devices.layoutManager = GridLayoutManager(context, 1)
        fragment_device_rv_devices.adapter = _adapter
    }

    override fun onStart() {
        super.onStart()
        updateList()
    }


    private fun initBindings() {
        _viewModel.event.observe(this, Observer { event ->
            when (event) {
                is DeviceState.Error -> {
                    Toast.makeText(
                        activity!!.applicationContext,
                        event.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is DeviceState.PullDevicesSuccessful -> {
                    _adapter.items = event.deviceList
                    _adapter.notifyDataSetChanged()
                }
                DeviceState.CreateNewDevice -> {
                    val createDeviceIntent =
                        Intent(activity?.applicationContext!!, CreateDeviceActivity::class.java)
                    startActivity(createDeviceIntent)
                }
                else -> {
                    Log.e(_TAG, "Unhandled event")
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

    private fun getToken(): String {
        return _sharedPref.getString("token", "")!!
    }

    private fun showPermissionLabel() {
        fragment_device_l_coordinator.visibility = View.INVISIBLE
        fragment_device_iv_permission.visibility = View.VISIBLE
        fragment_device_tv_permission.visibility = View.VISIBLE
        floatingActionButton.hide()
    }

    private fun showDeviceList() {
        fragment_device_l_coordinator.visibility = View.VISIBLE
        fragment_device_iv_permission.visibility = View.INVISIBLE
        fragment_device_tv_permission.visibility = View.INVISIBLE
        floatingActionButton.show()
    }

    private fun updateList() {
        val token = getToken()
        if (_viewModel.checkPermission(getUserObject())) {
            showDeviceList()
            _viewModel.pullDeviceList(token)
        } else {
            showPermissionLabel()
        }
    }


}