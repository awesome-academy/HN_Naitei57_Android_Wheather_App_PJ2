package com.sun.weather.ui.detail

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.weather.R
import com.sun.weather.base.BaseFragment
import com.sun.weather.databinding.FragmentDetailBinding
import com.sun.weather.ui.SharedViewModel
import com.sun.weather.utils.ext.goBackFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date
import java.util.Locale

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    override val viewModel: DetailViewModel by viewModel()
    override lateinit var sharedViewModel: SharedViewModel
    private lateinit var dailyAdapter: DailyAdapter
    private lateinit var hourlyAdapter: HourlyAdapter
    private var isNetworkAvailable: Boolean = true
    private var cityName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityName = it.getString(ARG_CITY_NAME)
        }
    }

    override fun initView() {
        sharedViewModel = activityViewModel<SharedViewModel>().value
        isNetworkAvailable = sharedViewModel.isNetworkAvailable.value ?: true
        binding.toolbar.findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            goBackFragment()
        }

        hourlyAdapter = HourlyAdapter(mutableListOf())
        binding.recyclerViewHourly.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = hourlyAdapter
        }

        dailyAdapter = DailyAdapter(mutableListOf())
        binding.recylerViewDaily.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dailyAdapter
        }
        binding.tvToolbarTitle.text =
            getString(R.string.city_name_app_bar, getString(R.string.forecast_detail), cityName)
        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        binding.tvCurrentTime.text = currentDate
    }

    override fun initData() {
        cityName?.let { name ->
            Log.v(MY_TAG, "initData: cityName: $name, isNetworkAvailable: $isNetworkAvailable")
            viewModel.loadWeeklyForecast(name, isNetworkAvailable, "vi")
            viewModel.loadHourlyForecast(name, isNetworkAvailable, "vi")
        }
    }

    override fun bindData() {
        viewModel.weeklyForecast.observe(viewLifecycleOwner) { forecast ->
            dailyAdapter.updateData(forecast)
        }

        viewModel.hourlyForecast.observe(viewLifecycleOwner) { forecast ->
            hourlyAdapter.updateData(forecast)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Log.d(MY_TAG, "onError: $errorMessage")
        }
    }

    companion object {
        private const val ARG_CITY_NAME = "city_name"
        const val MY_TAG = "DetailFragment"

        fun newInstance(cityName: String): DetailFragment {
            val fragment = DetailFragment()
            val args =
                Bundle().apply {
                    putString(ARG_CITY_NAME, cityName)
                }
            fragment.arguments = args
            return fragment
        }
    }
}
