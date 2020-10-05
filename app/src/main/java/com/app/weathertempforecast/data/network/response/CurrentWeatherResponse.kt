package com.app.weathertempforecast.data.network.response

import com.app.weathertempforecast.data.db.entity.Current
import com.app.weathertempforecast.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val location: WeatherLocation,
    @SerializedName("current")
    val current: Current
) {
}