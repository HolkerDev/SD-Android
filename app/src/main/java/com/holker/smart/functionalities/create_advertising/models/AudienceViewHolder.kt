package com.holker.smart.functionalities.create_advertising.models

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.holker.smart.R
import kotlinx.android.synthetic.main.card_audience_select.view.*

class AudienceViewHolder(
    val inflater: LayoutInflater,
    val parent: ViewGroup
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.card_audience_select, parent, false)) {

    private val _TAG = AudienceViewHolder::class.java.name

    fun bind(audienceSelect: AudienceSelect) {
        Log.i(_TAG, "Audience was bind")
        itemView.card_audience_select_name.text = audienceSelect.name
    }
}