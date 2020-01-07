package com.holker.smart.functionalities.main.fragments.devices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.holker.smart.R
import com.holker.smart.data.model.OwnDevice
import kotlinx.android.synthetic.main.card_device.view.*

class DeviceViewHolder(
    val inflater: LayoutInflater,
    val parent: ViewGroup
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.card_device, parent, false)) {
    fun bind(device: OwnDevice) {
        itemView.card_device_name.text = device.name
    }
}