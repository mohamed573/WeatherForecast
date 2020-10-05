package com.app.weathertempforecast.data.network.response


import com.app.weathertempforecast.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName


data class FutureWeatherResponse(
    @SerializedName("forecast")
    val futureWeatherEntries: ForecastDaysContainer,
    val location: WeatherLocation
)