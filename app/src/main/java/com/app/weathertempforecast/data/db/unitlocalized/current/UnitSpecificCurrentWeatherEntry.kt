package com.app.weathertempforecast.data.db.unitlocalized.current

interface UnitSpecificCurrentWeatherEntry {
    val temperature : Double
    val conditionText : String
    val conditionIconUrl : String
    val winSpeed : Double
    val windDirection : String
    val perceptionVolume : Double
    val feelsLikeTemperature : Double
    val visibility : Double

}