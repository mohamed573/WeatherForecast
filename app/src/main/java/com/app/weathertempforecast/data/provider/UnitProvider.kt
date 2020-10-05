package com.app.weathertempforecast.data.provider


import com.app.weathertempforecast.internal.UnitSystem


interface UnitProvider{
    fun getUnitSystem() : UnitSystem
    }
