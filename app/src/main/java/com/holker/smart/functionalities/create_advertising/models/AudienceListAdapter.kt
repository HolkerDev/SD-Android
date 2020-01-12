package com.holker.smart.functionalities.create_advertising.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AudienceListAdapter(var items: List<AudienceSelect>) :
    RecyclerView.Adapter<AudienceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudienceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AudienceViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AudienceViewHolder, position: Int) {
        val audience = items[position]
        holder.bind(audience)
    }

}