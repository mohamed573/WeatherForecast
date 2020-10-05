package com.app.weathertempforecast.data.network.response

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.app.weathertempforecast.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response


class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {
    private val appContext = context.applicationContext

    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
      if(!isOnline())
          throw NoConnectivityException()
        return  chain.proceed(chain.request())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isOnline () : Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkInfo = connectivityManager.getNetworkCapabilities(network)

        if (networkInfo == null){
            Log.d("Data mobile" ,"no network found" )
            return false
        }

        return  networkInfo.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}