package com.app.weathertempforecast.ui.weather.future.detail

import com.app.weathertempforecast.data.provider.UnitProvider
import com.app.weathertempforecast.data.repository.ForecastRepository
import com.app.weathertempforecast.internal.lazyDeferred
import com.app.weathertempforecast.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailsWeatherViewModel(
    private val detailsDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeferred {
        forecastRepository.getFutureWeatherByDate(detailsDate, super.isMetricUnit)
    }

}