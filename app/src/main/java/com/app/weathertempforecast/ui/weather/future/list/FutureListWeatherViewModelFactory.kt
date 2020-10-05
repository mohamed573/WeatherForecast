package com.app.weathertempforecast.ui.weather.future.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.weathertempforecast.data.provider.UnitProvider
import com.app.weathertempforecast.data.repository.ForecastRepository

class FutureListWeatherViewModelFactory(
    private val forecastRepository : ForecastRepository,
    private val unitProvider : UnitProvider
):ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
         return FutureListWeatherViewModel(
             forecastRepository,
             unitProvider
         ) as T
    }

}