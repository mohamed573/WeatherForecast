package com.app.weathertempforecast.ui.base

import androidx.lifecycle.ViewModel
import com.app.weathertempforecast.data.provider.UnitProvider
import com.app.weathertempforecast.data.repository.ForecastRepository
import com.app.weathertempforecast.internal.UnitSystem
import com.app.weathertempforecast.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository:ForecastRepository,
    unitProvider: UnitProvider
):ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit : Boolean
    get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}