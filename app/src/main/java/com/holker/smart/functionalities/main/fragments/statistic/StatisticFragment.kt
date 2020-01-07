package com.holker.smart.functionalities.main.fragments.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.holker.smart.R
import com.holker.smart.databinding.FragmentStatisticBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import javax.inject.Inject

class StatisticFragment : Fragment(), Injectable {
    private val TAG = StatisticFragment::class.java.name
    private lateinit var _viewModel: StatisticVM
    private lateinit var _binding: FragmentStatisticBinding


    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<StatisticVM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_statistic, container, false)
        _viewModel = ViewModelProviders.of(this, viewModelInjectionFactory)
            .get(StatisticVM::class.java)
        _binding.viewModel = _viewModel
        return _binding.root
    }
}