package com.holker.smart.functionalities.main.fragments.advertising

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.holker.smart.data.model.OwnAdvertising

class AdvertisingListAdapter(var items: List<OwnAdvertising>) :
    RecyclerView.Adapter<AdvertisingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertisingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AdvertisingViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AdvertisingViewHolder, position: Int) {
        val obj = items[position]
        holder.bind(obj)
    }
}