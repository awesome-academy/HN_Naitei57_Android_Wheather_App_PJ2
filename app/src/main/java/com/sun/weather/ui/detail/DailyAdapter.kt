package com.sun.weather.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sun.weather.data.model.WeatherBasic
import com.sun.weather.databinding.ForecastDailyBinding
import com.sun.weather.utils.Constant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyAdapter(private var forecastList: List<WeatherBasic>) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ForecastDailyBinding.inflate(inflater, parent, false))
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

    inner class ViewHolder(private val binding: ForecastDailyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: WeatherBasic) {
            val dayFormat = SimpleDateFormat(DAY_ONLY_PATTERN, Locale.getDefault())
            val day = dayFormat.format(Date(item.dateTime!! * SECOND_TO_MILLIS))
            binding.tvDay.text = day
            binding.tvStatus.text = item.weatherDescription
            binding.tvMaxTemp.text = String.format("%.1f", item.maxTemperature)+"°C"
            binding.tvMinTemp.text =String.format("%.1f", item.minTemperature)+"°C"
            val iconWeatherUrl = "${Constant.BASE_ICON_URL}${item.iconWeather}@2x.png"
            Glide.with(itemView.context.applicationContext)
                .load(iconWeatherUrl)
                .into(binding.imgStatus)
        }
    }

    fun updateData(list: List<WeatherBasic>) {
        forecastList = list
        notifyDataSetChanged()
    }
    companion object{
        const val DAY_ONLY_PATTERN = "dd-MM-yyyy"
        const val SECOND_TO_MILLIS = 1000
    }
}
