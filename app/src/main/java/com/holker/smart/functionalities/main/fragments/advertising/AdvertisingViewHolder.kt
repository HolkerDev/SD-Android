package com.holker.smart.functionalities.main.fragments.advertising

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.holker.smart.R
import com.holker.smart.data.model.OwnAdvertising
import com.holker.smart.utils.ConvertUtils
import kotlinx.android.synthetic.main.card_advertising.view.*

class AdvertisingViewHolder(
    val inflater: LayoutInflater,
    val parent: ViewGroup
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.card_advertising, parent, false)) {

    private val _TAG = AdvertisingViewHolder::class.java.name

    fun bind(advertising: OwnAdvertising) {
        itemView.card_advertising_name.text = advertising.name
        val dateString = ConvertUtils.dateToDateString(advertising.toDate)
        val timeString = ConvertUtils.dateToTimeString(advertising.toDate)
        itemView.card_advertising_time.text = "$dateString | $timeString"
    }
}