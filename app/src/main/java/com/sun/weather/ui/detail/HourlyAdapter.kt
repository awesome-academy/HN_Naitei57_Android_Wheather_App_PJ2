package com.sun.weather.ui.detail

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sun.weather.data.model.WeatherBasic
import com.sun.weather.databinding.ForecastHourlyBinding
import com.sun.weather.ui.detail.DailyAdapter.Companion.SECOND_TO_MILLIS
import com.sun.weather.utils.Constant
import java.util.Date
import java.util.Locale

class HourlyAdapter(private var forecastList: List<WeatherBasic>) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ForecastHourlyBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bindData(forecastList[position])
    }

    inner class ViewHolder(private val binding: ForecastHourlyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: WeatherBasic) {
            val timestamp =
                item.dateTime?.times(SECOND_TO_MILLIS)
            val date = Date(timestamp!!)
            val newFormat = SimpleDateFormat(HOUR_ONLY_PATTERN, Locale.getDefault())
            val timeString = newFormat.format(date)
            binding.forecastHourTv.text = timeString
            binding.forecastTemp.text = String.format("%.1f", item.currentTemperature) +"°C"
            val iconWeatherUrl = "${Constant.BASE_ICON_URL}${item.iconWeather}@2x.png"
            Glide.with(itemView.context.applicationContext)
                .load(iconWeatherUrl)
                .into(binding.statusImg)
        }
    }

    fun updateData(list: List<WeatherBasic>) {
        forecastList = list
        notifyDataSetChanged()
    }

    companion object{
        const val HOUR_ONLY_PATTERN = "HH:mm"
    }
}