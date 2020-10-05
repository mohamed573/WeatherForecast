package com.app.weathertempforecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.weathertempforecast.data.network.response.CurrentWeatherResponse

import com.app.weathertempforecast.data.network.response.FutureWeatherResponse
import com.app.weathertempforecast.internal.NoConnectivityException


const val FORECAST_DAYS_COUNT = 7

class WeatherNetworkDataSourceImpl(private val apiWeatherApiService: ApiWeatherApiService) :
    WeatherNetworkDataSource {


    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {

            val fetchedCurrentWeather = apiWeatherApiService
                .getCurrentWeather(location)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
        }
    }

    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchFutureWeather(
        location: String
    ) {
        try {
            val fetchedFutureWeather = apiWeatherApiService
                .getFutureWeather(location, FORECAST_DAYS_COUNT)
                .await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
        }
    }
}

