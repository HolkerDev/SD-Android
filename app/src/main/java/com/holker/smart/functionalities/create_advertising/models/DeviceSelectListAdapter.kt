package com.holker.smart.functionalities.create_advertising.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DeviceSelectListAdapter(var items: List<DeviceSelect>) :
    RecyclerView.Adapter<DeviceSelectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceSelectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DeviceSelectViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DeviceSelectViewHolder, position: Int) {
        val device = items[position]
        holder.bind(device)
    }

}