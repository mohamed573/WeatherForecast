package com.app.weathertempforecast

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.app.weathertempforecast.data.db.ForecastDatabase
import com.app.weathertempforecast.data.network.ApiWeatherApiService
import com.app.weathertempforecast.data.network.WeatherNetworkDataSource
import com.app.weathertempforecast.data.network.WeatherNetworkDataSourceImpl
import com.app.weathertempforecast.data.network.response.ConnectivityInterceptor
import com.app.weathertempforecast.data.network.response.ConnectivityInterceptorImpl
import com.app.weathertempforecast.data.provider.LocationProvider
import com.app.weathertempforecast.data.provider.LocationProviderImpl
import com.app.weathertempforecast.data.provider.UnitProvider
import com.app.weathertempforecast.data.provider.UnitProviderImpl
import com.app.weathertempforecast.data.repository.ForecastRepository
import com.app.weathertempforecast.data.repository.ForecastRepositoryImpl
import com.app.weathertempforecast.ui.weather.current.CurrentWeatherViewModelFactory
import com.app.weathertempforecast.ui.weather.future.detail.FutureDetailsWeatherViewModelFactory
import com.app.weathertempforecast.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class ForecastApplication : Application() , KodeinAware {
    override val kodein = Kodein.lazy {
    import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with  singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance() , instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance() , instance(), instance(),instance() , instance())}
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
        bind() from factory { detailsDate : LocalDate -> FutureDetailsWeatherViewModelFactory(detailsDate , instance() , instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this,R.xml.preferences , false)
    }
}