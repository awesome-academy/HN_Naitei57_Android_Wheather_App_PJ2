package com.sun.weather.di

import com.sun.weather.ui.MainViewModel
import com.sun.weather.ui.SharedViewModel
import com.sun.weather.ui.detail.DetailViewModel
import com.sun.weather.ui.favourite.FavouriteViewModel
import com.sun.weather.ui.home.HomeViewModel
import com.sun.weather.ui.search.SearchViewModel
import com.sun.weather.ui.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val ViewModelModule: Module = module {
    viewModel { MainViewModel(get()) }
    viewModel { SharedViewModel() }
    viewModel { HomeViewModel(get(),get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavouriteViewModel(get()) }
    viewModel { SettingViewModel() }
}
