package com.app.weathertempforecast.data.db.unitlocalized.current

import androidx.room.ColumnInfo

data class MetricCurrentWeatherEntry(

    @ColumnInfo(name = "tempC")
    override val temperature: Double,

    @ColumnInfo(name = "windKph")
    override val winSpeed: Double,

    @ColumnInfo(name = "condition_text")
    override val conditionText: String,

    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,

    @ColumnInfo(name = "windDir")
    override val windDirection: String,

    @ColumnInfo(name = "precipMm")
    override val perceptionVolume: Double,

    @ColumnInfo(name = "feelslikeC")
    override val feelsLikeTemperature: Double,

    @ColumnInfo(name = "visKm")
    override val visibility: Double


) : UnitSpecificCurrentWeatherEntry {



}