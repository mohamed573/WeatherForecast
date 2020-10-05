package com.app.weathertempforecast.data.provider

import com.app.weathertempforecast.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation : WeatherLocation) : Boolean
    suspend fun getPreferredLocationString() : String
}