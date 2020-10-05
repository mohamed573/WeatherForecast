package com.app.weathertempforecast.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.weathertempforecast.data.provider.UnitProvider
import com.app.weathertempforecast.data.repository.ForecastRepository


// Preservation of viewModels is a job for a viewModelProvider
// we only pass the factory into the provider and the factory works as usual , creating new instance of object


class CurrentWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
):ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository , unitProvider) as T
    }
}