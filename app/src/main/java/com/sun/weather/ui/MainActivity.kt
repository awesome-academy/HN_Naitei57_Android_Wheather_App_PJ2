package com.sun.weather.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.sun.weather.R
import com.sun.weather.base.BaseActivity
import com.sun.weather.databinding.ActivityMainBinding
import com.sun.weather.notification.DailyWorker
import com.sun.weather.ui.favourite.FavouriteFragment
import com.sun.weather.ui.home.HomeFragment
import com.sun.weather.ui.setting.SettingFragment
import com.sun.weather.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override val viewModel: MainViewModel by viewModel()
    override val sharedViewModel: SharedViewModel by viewModel()

    override fun initialize() {
        viewModel.locationData.observe(
            this,
            Observer { location ->
                val (latitude, longitude) = location
                sharedViewModel.setLocation(latitude, longitude)
                val homeFragment = HomeFragment.newInstance()
                setNextFragment(homeFragment)
            },
        )
        viewModel.requestLocationAndFetchWeather(this)
        setNavigation()
        scheduleDailyWorkAtSpecificTime(0, 28)
    }

    private fun setNextFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(fragment::javaClass.name)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun setNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mi_home -> setNextFragment(HomeFragment.newInstance())
                R.id.mi_favorite -> setNextFragment(FavouriteFragment.newInstance())
                R.id.mi_setting -> setNextFragment(SettingFragment.newInstance())
            }
            true
        }
    }

    private fun scheduleDailyWorkAtSpecificTime(
        hour: Int,
        minute: Int,
    ) {
        val currentTime = Calendar.getInstance()

        val targetTime =
            Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                if (before(currentTime)) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }
            }

        val initialDelay = targetTime.timeInMillis - currentTime.timeInMillis

        val constraints =
            Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

        val myRequest =
            PeriodicWorkRequest.Builder(
                DailyWorker::class.java,
                24,
                TimeUnit.HOURS,
            ).setConstraints(constraints)
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build()

        WorkManager.getInstance(this).cancelUniqueWork(Constant.DAILY_WORK_MANAGER_ID)
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            Constant.DAILY_WORK_MANAGER_ID,
            ExistingPeriodicWorkPolicy.REPLACE,
            myRequest,
        )
    }
}
