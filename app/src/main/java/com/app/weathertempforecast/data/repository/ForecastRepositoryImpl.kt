package com.app.weathertempforecast.data.repository

import androidx.lifecycle.LiveData
import com.app.weathertempforecast.data.db.CurrentWeatherDao
import com.app.weathertempforecast.data.db.FutureWeatherDao
import com.app.weathertempforecast.data.db.WeatherLocationDao
import com.app.weathertempforecast.data.db.entity.WeatherLocation
import com.app.weathertempforecast.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.app.weathertempforecast.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.app.weathertempforecast.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.app.weathertempforecast.data.network.FORECAST_DAYS_COUNT
import com.app.weathertempforecast.data.network.WeatherNetworkDataSource
import com.app.weathertempforecast.data.network.response.CurrentWeatherResponse
import com.app.weathertempforecast.data.network.response.FutureWeatherResponse
import com.app.weathertempforecast.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime

import java.util.*

// Repository is a class which is put the network and the local data operations into a centralized place

class ForecastRepositoryImpl(private val currentWeatherDao : CurrentWeatherDao,
                             private val futureWeatherDao : FutureWeatherDao,
                             private val weatherLocationDao : WeatherLocationDao,
                             private val weatherNetworkDataSource : WeatherNetworkDataSource,
                              private val locationProvider : LocationProvider)

    : ForecastRepository {

    init {
        weatherNetworkDataSource.apply {
            downloadCurrentWeather.observeForever { newCurrentWeather ->
                // persist it into db
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever { newFutureWeather ->
                // persist it into db
                persistFetchedFutureWeather(newFutureWeather)

            }
        }
    }


        override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {

            // withContext returns a value
            return withContext(Dispatchers.IO) {
                initWeatherData()
                return@withContext if (metric) currentWeatherDao.getWeatherMetric()
                else currentWeatherDao.getWeatherImperial()
            }
        }

        override suspend fun getFutureWeatherList(
            startDate: LocalDate,
            metric: Boolean
        ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
            return withContext(Dispatchers.IO) {
                initWeatherData()
                return@withContext if (metric) futureWeatherDao.getSimpleWeatherForecastMetric(
                    startDate
                )
                else futureWeatherDao.getSimpleWeatherForecastImperial(startDate)
            }
        }

    override suspend fun getFutureWeatherByDate(
        date: LocalDate,
        metric: Boolean
    ): LiveData<out UnitSpecificDetailFutureWeatherEntry> {
        return  withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) futureWeatherDao.getDetailedWeatherByDateMetric(date)
            else futureWeatherDao.getDetailedWeatherByDateImperial(date)
        }
    }

        override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
            return withContext((Dispatchers.IO)) {
                return@withContext weatherLocationDao.getLocation()
            }
        }

        private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
            // launch does not returns a value just a job only
            GlobalScope.launch(Dispatchers.IO) {
                currentWeatherDao.upsert(fetchedWeather.current)
                weatherLocationDao.upsert(fetchedWeather.location)
            }
        }

         private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse){

             fun deleteOldForecastData() {
                 val today = LocalDate.now()
                 futureWeatherDao.deleteOldEntries(today)
             }
             GlobalScope.launch (Dispatchers.IO){ val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
                 deleteOldForecastData()
                 futureWeatherDao.insert(futureWeatherList)
                 weatherLocationDao.upsert(fetchedWeather.location)
             }

             }

        private suspend fun initWeatherData() {
            val lastWeatherLocation = weatherLocationDao.getLocationNonLive()

            if (lastWeatherLocation == null
                || locationProvider.hasLocationChanged(lastWeatherLocation)
            ) {
                fetchCurrentWeather()
                fetchFutureWeather()
                return
            }
            if (isFetchedCurrentNeeded(lastWeatherLocation.zonedDateTime))
                fetchCurrentWeather()

             if(isFetchedFutureNeeded())
            fetchFutureWeather()

        }

        private suspend fun fetchCurrentWeather() {
            weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString())
            Locale.getDefault()
            // TODO : option you can add language
        }


         private suspend fun fetchFutureWeather(){
             weatherNetworkDataSource.fetchFutureWeather(locationProvider.getPreferredLocationString())
             Locale.getDefault()
             // TODO : option you can add language
         }


        private fun isFetchedCurrentNeeded(lastFetchedTime: ZonedDateTime): Boolean {
            val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
            return lastFetchedTime.isBefore(thirtyMinutesAgo)
        }


        private fun isFetchedFutureNeeded() : Boolean{
            val today = LocalDate.now()
            val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
            return futureWeatherCount < FORECAST_DAYS_COUNT
        }

    
    }
