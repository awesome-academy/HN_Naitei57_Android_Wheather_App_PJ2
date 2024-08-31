package com.sun.weather.di

import com.sun.weather.ui.MainViewModel
import com.sun.weather.ui.SharedViewModel
import com.sun.weather.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val ViewModelModule: Module =
    module {
        viewModel { MainViewModel(get()) }
        viewModel { SharedViewModel() }
        viewModel { HomeViewModel(get()) }
    }
