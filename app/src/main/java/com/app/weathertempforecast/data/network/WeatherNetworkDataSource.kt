package com.app.weathertempforecast.data.network

import androidx.lifecycle.LiveData
import com.app.weathertempforecast.data.network.response.CurrentWeatherResponse
import com.app.weathertempforecast.data.network.response.FutureWeatherResponse

interface WeatherNetworkDataSource {
    val downloadCurrentWeather : LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather : LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location : String
    )

    suspend fun fetchFutureWeather(
        location: String,

    )
}