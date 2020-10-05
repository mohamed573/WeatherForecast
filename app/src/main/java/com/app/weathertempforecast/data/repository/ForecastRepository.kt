package com.app.weathertempforecast.data.repository

import androidx.lifecycle.LiveData
import com.app.weathertempforecast.data.db.entity.WeatherLocation
import com.app.weathertempforecast.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.app.weathertempforecast.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.app.weathertempforecast.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate


interface ForecastRepository {
    // suspend fun to call it from Coroutine
    suspend fun getCurrentWeather(metric : Boolean) : LiveData< out UnitSpecificCurrentWeatherEntry>

    suspend fun getFutureWeatherList(startDate : LocalDate, metric: Boolean) : LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>

    suspend fun getFutureWeatherByDate(date: LocalDate , metric: Boolean) : LiveData<out UnitSpecificDetailFutureWeatherEntry>

    suspend fun getWeatherLocation() : LiveData<WeatherLocation>



}