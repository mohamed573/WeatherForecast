package com.app.weathertempforecast.internal

import java.io.IOException

class NoConnectivityException : IOException ()
class LocationPermissionGrantedException : Exception()

class DateNotFoundException() : Exception()