package com.app.weathertempforecast.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.weathertempforecast.data.provider.UnitProvider
import com.app.weathertempforecast.data.repository.ForecastRepository
import org.threeten.bp.LocalDate

class FutureDetailsWeatherViewModelFactory(
    private val detailsDate : LocalDate,
    private val forecastRepository: ForecastRepository,
    private val unitProvider : UnitProvider
) :ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureDetailsWeatherViewModel(detailsDate , forecastRepository , unitProvider) as T
    }
}