package com.app.weathertempforecast.ui.weather.future.list

import android.annotation.SuppressLint
import android.util.Log
import com.app.weathertempforecast.R
import com.app.weathertempforecast.data.db.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import com.app.weathertempforecast.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.app.weathertempforecast.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_future_weather.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

private const val TAG = "FutureWeatherItem"

class FutureWeatherItem(
    val weatherEntry: UnitSpecificSimpleFutureWeatherEntry
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        Log.d(TAG , "bind : Starts")
        viewHolder.apply {
            textView_condition.text = weatherEntry.conditionText
            updateDate()
            updateTemperature()
            updateConditionImage()
        }
    }

    override fun getLayout() = R.layout.item_future_weather


    private fun ViewHolder.updateDate() {
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        textView_date.text = weatherEntry.date.format(dtFormatter)
    }


    private fun ViewHolder.updateTemperature() {
        Log.d(TAG , "ViewHolder.updateTemperature() : called")
        val unitAbbreviation = if (weatherEntry is MetricSimpleFutureWeatherEntry) "°C"
        else "°F"
        textView_temperature.text = "${weatherEntry.avgTemperature}$unitAbbreviation"

    }

    private fun ViewHolder.updateConditionImage() {
        Log.d(TAG , "ViewHolder.updateConditionImage() : called")
        GlideApp.with(this.containerView)
            .load("http: " + weatherEntry.conditionIconUrl)
            .into(imageView_condition_icon)
    }


}

