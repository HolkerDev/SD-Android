package com.holker.smart.functionalities.main.fragments.advertising

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.holker.smart.R
import com.holker.smart.databinding.FragmentAdvertisingBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import javax.inject.Inject

class AdvertisingFragment : Fragment(), Injectable {
    private val TAG = AdvertisingFragment::class.java.name
    private lateinit var _viewModel: AdvertisingVM
    private lateinit var _binding: FragmentAdvertisingBinding


    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<AdvertisingVM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_advertising, container, false)
        _viewModel = ViewModelProviders.of(this, viewModelInjectionFactory)
            .get(AdvertisingVM::class.java)
        _binding.viewModel = _viewModel
        return _binding.root

    }
}