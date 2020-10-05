package com.app.weathertempforecast.ui.weather.current

import com.app.weathertempforecast.data.provider.UnitProvider
import com.app.weathertempforecast.data.repository.ForecastRepository
import com.app.weathertempforecast.internal.lazyDeferred
import com.app.weathertempforecast.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) :
    WeatherViewModel(forecastRepository, unitProvider) {

    // viewModel will communicate with Repository and then viewModel expose data to the view and then the view will fetch the data
    // Actually , preservation of viewModels is a job a viewModelProvider
    // we only pass the factory into the provider

    // Factory work as usual , creating new instance of objects


    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(super.isMetricUnit)

    }


}