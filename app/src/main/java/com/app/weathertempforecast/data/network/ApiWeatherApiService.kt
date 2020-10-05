package com.app.weathertempforecast.data.network



import com.app.weathertempforecast.data.network.response.ConnectivityInterceptor
import com.app.weathertempforecast.data.network.response.CurrentWeatherResponse
import com.app.weathertempforecast.data.network.response.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



const val API_KEY = " "

/**
 *  please check the below URL to create you own API key
 *     https://www.weatherapi.com/
 */


//  https://api.weatherapi.com/v1/current.json?key=""

interface ApiWeatherApiService {

    @GET("current.json")
   fun getCurrentWeather(
        @Query("q") location : String
    ) : Deferred<CurrentWeatherResponse>


    // https://api.weatherapi.com/v1/forecast.json?key=
    @GET("forecast.json")
    fun getFutureWeather(
        @Query("q") location :String,
        @Query("days") days : Int,
   //     @Query("lang") languageCode : String = "en"
    ) :Deferred<FutureWeatherResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor : ConnectivityInterceptor
        ) : ApiWeatherApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key" , API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()



                return@Interceptor chain.proceed(request)
            }

            val OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()



            return Retrofit.Builder()
                .client(OkHttpClient)
                .baseUrl("https://api.weatherapi.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiWeatherApiService::class.java)
        }

    }
}