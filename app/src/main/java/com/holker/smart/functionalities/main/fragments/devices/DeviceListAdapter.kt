package com.holker.smart.functionalities.main.fragments.devices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.holker.smart.data.model.OwnDevice

class DeviceListAdapter(var items: List<OwnDevice>) :
    RecyclerView.Adapter<DeviceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DeviceViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val note = items[position]
        holder.bind(note)
    }
}