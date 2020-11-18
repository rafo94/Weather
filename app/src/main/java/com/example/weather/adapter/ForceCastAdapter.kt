package com.example.weather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.weather.R
import com.example.weather.model.Forecastday
import com.example.weather.util.getImageByCode
import java.util.*

class ForceCastAdapter(
    private var foreCastDayList: ArrayList<Forecastday>,
) : RecyclerView.Adapter<ForceCastAdapter.ForeCastHolder>() {

    var callback: ((Forecastday, View) -> Unit)? = null

    fun setData(forceCastList: List<Forecastday>) {
        if (this.foreCastDayList.isNotEmpty())
            this.foreCastDayList.clear()
        this.foreCastDayList.addAll(forceCastList)
        notifyDataSetChanged()
    }

    inner class ForeCastHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val weatherItemStateLav: LottieAnimationView =
            itemView.findViewById(R.id.weatherItemStateLav)
        private val weatherItemDayTv: TextView = itemView.findViewById(R.id.weatherItemDayTv)
        private val weatherItemTempTv: TextView = itemView.findViewById(R.id.weatherItemTempTv)

        @SuppressLint("SetTextI18n")
        fun bind(forecastDay: Forecastday) {
            val day = forecastDay.day
            weatherItemStateLav.getImageByCode(day.condition.code, 1)
            weatherItemDayTv.text = forecastDay.date
            weatherItemTempTv.text = "${day.maxtemp_c.toInt()}°"
            itemView.setOnClickListener { callback?.invoke(forecastDay, it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForeCastHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return ForeCastHolder(view)
    }

    override fun onBindViewHolder(holder: ForeCastHolder, position: Int) {
        val foreCastDay = foreCastDayList[position]
        holder.bind(foreCastDay)
    }

    override fun getItemCount() = foreCastDayList.size
}