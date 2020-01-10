package com.holker.smart.functionalities.main.fragments.advertising

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.holker.smart.R
import com.holker.smart.databinding.FragmentAdvertisingBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import com.holker.smart.functionalities.create_advertising.CreateAdvertisingActivity
import kotlinx.android.synthetic.main.fragment_advertising.*
import javax.inject.Inject

class AdvertisingFragment : Fragment(), Injectable {
    private val _TAG = AdvertisingFragment::class.java.name
    private lateinit var _viewModel: AdvertisingVM
    private lateinit var _binding: FragmentAdvertisingBinding

    private lateinit var _sharedPref: SharedPreferences
    private lateinit var _adapter: AdvertisingListAdapter


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set up shared preference
        _sharedPref = activity?.applicationContext!!.getSharedPreferences(
            getString(R.string.preference_key),
            Context.MODE_PRIVATE
        )

        initBinding()

        //set up adapter
        _adapter = AdvertisingListAdapter(listOf())
        fragment_advertising_rv_devices.layoutManager = GridLayoutManager(context, 1)
        fragment_advertising_rv_devices.adapter = _adapter
    }

    private fun initBinding() {
        _viewModel.event.observe(this, Observer { event ->
            when (event) {
                AdvertisingState.CreateAdvertising -> {
                    val createAdvertisingIntent = Intent(
                        activity?.applicationContext!!,
                        CreateAdvertisingActivity::class.java
                    )
                    startActivity(createAdvertisingIntent)
                }
            }
        })
    }
}