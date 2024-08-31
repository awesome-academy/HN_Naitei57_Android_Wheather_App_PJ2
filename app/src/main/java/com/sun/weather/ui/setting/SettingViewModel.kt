package com.sun.weather.ui.setting

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sun.weather.R
import com.sun.weather.ui.setting.SettingFragment.Companion.DEFAULT_FLAG_RES_ID
import com.sun.weather.ui.setting.SettingFragment.Companion.KEY_FLAG_RES_ID
import com.sun.weather.ui.setting.SettingFragment.Companion.KEY_LANGUAGE_CODE
import com.sun.weather.utils.SharedPrefManager
import java.util.Locale

class SettingViewModel : ViewModel() {
    private val _flagResId = MutableLiveData<Int>()
    val flagResId: LiveData<Int> get() = _flagResId

    init {
        _flagResId.value = SharedPrefManager.getInt(KEY_FLAG_RES_ID, DEFAULT_FLAG_RES_ID)
    }

    fun setFlagResId(flagResId: Int) {
        _flagResId.value = flagResId
        SharedPrefManager.putInt(KEY_FLAG_RES_ID, flagResId)
    }

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        SharedPrefManager.putString(KEY_LANGUAGE_CODE, languageCode)
    }
}
