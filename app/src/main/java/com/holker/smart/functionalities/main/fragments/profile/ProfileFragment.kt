package com.holker.smart.functionalities.main.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.holker.smart.R
import com.holker.smart.databinding.FragmentProfileBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import javax.inject.Inject

class ProfileFragment : Fragment(), Injectable {
    private val TAG = ProfileFragment::class.java.name
    private lateinit var _viewModel: ProfileVM
    private lateinit var _binding: FragmentProfileBinding


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
}