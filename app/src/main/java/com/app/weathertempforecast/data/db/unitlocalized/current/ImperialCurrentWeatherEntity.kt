package com.app.weathertempforecast.data.db.unitlocalized.current

import androidx.room.ColumnInfo

data class ImperialCurrentWeatherEntity(
    @ColumnInfo(name = "tempF")
    override val temperature: Double,

    @ColumnInfo(name = "condition_text")
    override val conditionText: String,

    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,

    @ColumnInfo(name = "windMph")
    override val winSpeed: Double,

    @ColumnInfo(name = "windDir")
    override val windDirection: String,

    @ColumnInfo(name = "precipIn")
    override val perceptionVolume: Double,

    @ColumnInfo(name = "feelslikeF")
    override val feelsLikeTemperature: Double,

    @ColumnInfo(name = "visMiles")
    override val visibility: Double


    ) : UnitSpecificCurrentWeatherEntry {


}