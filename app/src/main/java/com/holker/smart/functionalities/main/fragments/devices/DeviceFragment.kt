package com.holker.smart.functionalities.main.fragments.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.holker.smart.R
import com.holker.smart.databinding.FragmentDeviceBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import javax.inject.Inject

class DeviceFragment : Fragment(), Injectable {
    private val TAG = DeviceFragment::class.java.name
    private lateinit var _viewModel: DeviceVM
    private lateinit var _binding: FragmentDeviceBinding


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
        return _binding.root
    }
}