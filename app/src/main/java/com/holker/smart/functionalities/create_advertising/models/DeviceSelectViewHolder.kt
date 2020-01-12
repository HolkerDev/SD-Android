package com.holker.smart.functionalities.create_advertising.models

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.holker.smart.R
import kotlinx.android.synthetic.main.card_device_select.view.*

class DeviceSelectViewHolder(
    val inflater: LayoutInflater,
    val parent: ViewGroup
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.card_device_select, parent, false)) {

    private val _TAG = DeviceSelectViewHolder::class.java.name

    fun bind(deviceSelect: DeviceSelect) {
        Log.i(_TAG, "Device was bind")
        itemView.card_device_select_name.text = deviceSelect.name
    }
}
