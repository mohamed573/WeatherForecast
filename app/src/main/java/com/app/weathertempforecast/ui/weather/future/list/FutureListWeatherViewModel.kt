package com.app.weathertempforecast.ui.weather.future.list

import com.app.weathertempforecast.data.provider.UnitProvider
import com.app.weathertempforecast.data.repository.ForecastRepository
import com.app.weathertempforecast.internal.lazyDeferred
import com.app.weathertempforecast.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
     unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository , unitProvider) {
    val weatherEntries by lazyDeferred {
        forecastRepository.getFutureWeatherList(LocalDate.now() , super.isMetricUnit)

    }
}