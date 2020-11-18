package com.example.weather.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.example.weather.R
import com.example.weather.util.Constants.Companion.FORCE_CAST_BLIZZARD
import com.example.weather.util.Constants.Companion.FORCE_CAST_BLOWING_SNOW
import com.example.weather.util.Constants.Companion.FORCE_CAST_CLOUDY
import com.example.weather.util.Constants.Companion.FORCE_CAST_FOG
import com.example.weather.util.Constants.Companion.FORCE_CAST_FREEZING_DRIZZLE
import com.example.weather.util.Constants.Companion.FORCE_CAST_FREEZING_FOG
import com.example.weather.util.Constants.Companion.FORCE_CAST_HEAVY_FREEZING_DRIZZLE
import com.example.weather.util.Constants.Companion.FORCE_CAST_HEAVY_RAIN
import com.example.weather.util.Constants.Companion.FORCE_CAST_HEAVY_RAIN_AT_TIMES
import com.example.weather.util.Constants.Companion.FORCE_CAST_HEAVY_SNOW
import com.example.weather.util.Constants.Companion.FORCE_CAST_ICE_PELLETS
import com.example.weather.util.Constants.Companion.FORCE_CAST_LIGHT_DRIZZLE
import com.example.weather.util.Constants.Companion.FORCE_CAST_LIGHT_FREEZING_RAIN
import com.example.weather.util.Constants.Companion.FORCE_CAST_LIGHT_RAIN
import com.example.weather.util.Constants.Companion.FORCE_CAST_LIGHT_RAIN_SHOWER
import com.example.weather.util.Constants.Companion.FORCE_CAST_LIGHT_SHOWERS_OF_ICE_PELLETS
import com.example.weather.util.Constants.Companion.FORCE_CAST_LIGHT_SLEET
import com.example.weather.util.Constants.Companion.FORCE_CAST_LIGHT_SNOW
import com.example.weather.util.Constants.Companion.FORCE_CAST_LIGHT_SNOW_SHOWERS
import com.example.weather.util.Constants.Companion.FORCE_CAST_MIST
import com.example.weather.util.Constants.Companion.FORCE_CAST_MODERATE_OR_HEAVY_FREEZING_RAIN
import com.example.weather.util.Constants.Companion.FORCE_CAST_MODERATE_OR_HEAVY_SHOWER
import com.example.weather.util.Constants.Companion.FORCE_CAST_MODERATE_OR_HEAVY_SHOWERS_OF_ICE_PELLETS
import com.example.weather.util.Constants.Companion.FORCE_CAST_MODERATE_OR_HEAVY_SLEET
import com.example.weather.util.Constants.Companion.FORCE_CAST_MODERATE_OR_HEAVY_SLEET_SHOWERS
import com.example.weather.util.Constants.Companion.FORCE_CAST_MODERATE_OR_HEAVY_SNOW_SHOWERS
import com.example.weather.util.Constants.Companion.FORCE_CAST_MODERATE_OR_HEAVY_SNOW_WITH_THUNDER
import com.example.weather.util.Constants.Companion.FORCE_CAST_MODERATE_OR_HEAVY_WITH_THUNDER
import com.example.weather.util.Constants.Companion.FORCE_CAST_MODERATE_RAIN
import com.example.weather.util.Constants.Companion.FORCE_CAST_MODERATE_RAIN_AT_TIMES
import com.example.weather.util.Constants.Companion.FORCE_CAST_MODERATE_SNOW
import com.example.weather.util.Constants.Companion.FORCE_CAST_OVERCAST
import com.example.weather.util.Constants.Companion.FORCE_CAST_PARTLY_CLOUDY
import com.example.weather.util.Constants.Companion.FORCE_CAST_PATCHY_FREEZING_DRIZZLE_POSSIBLE
import com.example.weather.util.Constants.Companion.FORCE_CAST_PATCHY_HEAVY_SNOW
import com.example.weather.util.Constants.Companion.FORCE_CAST_PATCHY_LIGHT_DRIZZLE
import com.example.weather.util.Constants.Companion.FORCE_CAST_PATCHY_LIGHT_RAIN
import com.example.weather.util.Constants.Companion.FORCE_CAST_PATCHY_LIGHT_RAIN_WITH_THUNDER
import com.example.weather.util.Constants.Companion.FORCE_CAST_PATCHY_LIGHT_SNOW
import com.example.weather.util.Constants.Companion.FORCE_CAST_PATCHY_LIGHT_SNOW_WITH_THUNDER
import com.example.weather.util.Constants.Companion.FORCE_CAST_PATCHY_MODERATE_SNOW
import com.example.weather.util.Constants.Companion.FORCE_CAST_PATCHY_RAIN_POSSIBLE
import com.example.weather.util.Constants.Companion.FORCE_CAST_PATCHY_SLEET_POSSIBLE
import com.example.weather.util.Constants.Companion.FORCE_CAST_PATCHY_SNOW_POSSIBLE
import com.example.weather.util.Constants.Companion.FORCE_CAST_SHOWER_SLEET_SHOWERS
import com.example.weather.util.Constants.Companion.FORCE_CAST_SUNNY
import com.example.weather.util.Constants.Companion.FORCE_CAST_THUNDERY_OUTBREAKS_POSSIBLE
import com.example.weather.util.Constants.Companion.FORCE_CAST_TORRENTIAL_RAIN_SHOWER
import kotlin.math.hypot

fun LottieAnimationView.getImageByCode(code: Int, dayState: Int) {

    val isDay = dayState == 1

    val jsonName = when (code) {

        FORCE_CAST_SUNNY -> if (isDay) "weather_sunny.json" else "weather_night.json"

        FORCE_CAST_PARTLY_CLOUDY, FORCE_CAST_LIGHT_SLEET -> if (isDay) "weather_partly_cloudy.json" else "weather_cloudy_night.json"

        FORCE_CAST_CLOUDY, FORCE_CAST_OVERCAST -> "weather_windy.json"

        FORCE_CAST_MIST, FORCE_CAST_FOG, FORCE_CAST_FREEZING_FOG -> "weather_mist.json"

        FORCE_CAST_PATCHY_RAIN_POSSIBLE, FORCE_CAST_PATCHY_SLEET_POSSIBLE, FORCE_CAST_PATCHY_LIGHT_RAIN,
        FORCE_CAST_LIGHT_RAIN, FORCE_CAST_MODERATE_RAIN_AT_TIMES, FORCE_CAST_MODERATE_RAIN,
        FORCE_CAST_LIGHT_FREEZING_RAIN, FORCE_CAST_LIGHT_DRIZZLE, FORCE_CAST_PATCHY_LIGHT_DRIZZLE,
        FORCE_CAST_FREEZING_DRIZZLE, FORCE_CAST_MODERATE_OR_HEAVY_FREEZING_RAIN,
        FORCE_CAST_MODERATE_OR_HEAVY_SLEET, FORCE_CAST_LIGHT_RAIN_SHOWER ->
            if (isDay) "weather_partly_shower.json" else "weather_rainy_night.json"

        FORCE_CAST_PATCHY_SNOW_POSSIBLE, FORCE_CAST_BLOWING_SNOW, FORCE_CAST_PATCHY_FREEZING_DRIZZLE_POSSIBLE,
        FORCE_CAST_PATCHY_LIGHT_SNOW, FORCE_CAST_LIGHT_SNOW, FORCE_CAST_PATCHY_MODERATE_SNOW,
        FORCE_CAST_SHOWER_SLEET_SHOWERS, FORCE_CAST_MODERATE_OR_HEAVY_SLEET_SHOWERS,
        FORCE_CAST_LIGHT_SNOW_SHOWERS, FORCE_CAST_PATCHY_LIGHT_SNOW_WITH_THUNDER ->
            if (isDay) "weather_snow_sunny.json" else "weather_snow_night.json"

        FORCE_CAST_THUNDERY_OUTBREAKS_POSSIBLE -> "weather_thunder.json"

        FORCE_CAST_BLIZZARD, FORCE_CAST_HEAVY_FREEZING_DRIZZLE, FORCE_CAST_MODERATE_SNOW,
        FORCE_CAST_PATCHY_HEAVY_SNOW, FORCE_CAST_HEAVY_SNOW, FORCE_CAST_MODERATE_OR_HEAVY_SNOW_SHOWERS,
        FORCE_CAST_MODERATE_OR_HEAVY_SNOW_WITH_THUNDER -> "weather_snow.json"

        FORCE_CAST_HEAVY_RAIN_AT_TIMES, FORCE_CAST_HEAVY_RAIN, FORCE_CAST_ICE_PELLETS,
        FORCE_CAST_TORRENTIAL_RAIN_SHOWER, FORCE_CAST_LIGHT_SHOWERS_OF_ICE_PELLETS,
        FORCE_CAST_MODERATE_OR_HEAVY_SHOWERS_OF_ICE_PELLETS, FORCE_CAST_MODERATE_OR_HEAVY_WITH_THUNDER
        -> "weather_storm.json"

        FORCE_CAST_MODERATE_OR_HEAVY_SHOWER, FORCE_CAST_PATCHY_LIGHT_RAIN_WITH_THUNDER -> "weather_storm_showers_day.json"

        else -> ""
    }
    this.setAnimation(jsonName)
    this.playAnimation()
}

fun ConstraintLayout.getImageByCode(code: Int, dayState: Int) {

    val isDay = dayState == 1

    val imageId = when (code) {

        FORCE_CAST_SUNNY -> if (isDay) R.drawable.sky_blue else R.drawable.night_sky

        FORCE_CAST_PARTLY_CLOUDY, FORCE_CAST_LIGHT_SLEET -> if (isDay) R.drawable.partly_cloud else R.drawable.night_partly

        FORCE_CAST_CLOUDY, FORCE_CAST_OVERCAST -> R.drawable.id_cloud

        FORCE_CAST_MIST, FORCE_CAST_FOG, FORCE_CAST_FREEZING_FOG -> R.drawable.mist

        FORCE_CAST_PATCHY_RAIN_POSSIBLE, FORCE_CAST_PATCHY_SLEET_POSSIBLE, FORCE_CAST_PATCHY_LIGHT_RAIN,
        FORCE_CAST_LIGHT_RAIN, FORCE_CAST_MODERATE_RAIN_AT_TIMES, FORCE_CAST_MODERATE_RAIN,
        FORCE_CAST_LIGHT_FREEZING_RAIN, FORCE_CAST_LIGHT_DRIZZLE, FORCE_CAST_PATCHY_LIGHT_DRIZZLE,
        FORCE_CAST_FREEZING_DRIZZLE, FORCE_CAST_MODERATE_OR_HEAVY_FREEZING_RAIN,
        FORCE_CAST_MODERATE_OR_HEAVY_SLEET, FORCE_CAST_LIGHT_RAIN_SHOWER ->
            if (isDay) R.drawable.partly_shower else R.drawable.rainy_night

        FORCE_CAST_PATCHY_SNOW_POSSIBLE, FORCE_CAST_BLOWING_SNOW, FORCE_CAST_PATCHY_FREEZING_DRIZZLE_POSSIBLE,
        FORCE_CAST_PATCHY_LIGHT_SNOW, FORCE_CAST_LIGHT_SNOW, FORCE_CAST_PATCHY_MODERATE_SNOW,
        FORCE_CAST_SHOWER_SLEET_SHOWERS, FORCE_CAST_MODERATE_OR_HEAVY_SLEET_SHOWERS,
        FORCE_CAST_LIGHT_SNOW_SHOWERS, FORCE_CAST_PATCHY_LIGHT_SNOW_WITH_THUNDER ->
            if (isDay) R.drawable.snow_sunny else R.drawable.snow_night

        FORCE_CAST_THUNDERY_OUTBREAKS_POSSIBLE -> R.drawable.thunder

        FORCE_CAST_BLIZZARD, FORCE_CAST_HEAVY_FREEZING_DRIZZLE, FORCE_CAST_MODERATE_SNOW,
        FORCE_CAST_PATCHY_HEAVY_SNOW, FORCE_CAST_HEAVY_SNOW, FORCE_CAST_MODERATE_OR_HEAVY_SNOW_SHOWERS,
        FORCE_CAST_MODERATE_OR_HEAVY_SNOW_WITH_THUNDER -> R.drawable.snow

        FORCE_CAST_HEAVY_RAIN_AT_TIMES, FORCE_CAST_HEAVY_RAIN, FORCE_CAST_ICE_PELLETS,
        FORCE_CAST_TORRENTIAL_RAIN_SHOWER, FORCE_CAST_LIGHT_SHOWERS_OF_ICE_PELLETS,
        FORCE_CAST_MODERATE_OR_HEAVY_SHOWERS_OF_ICE_PELLETS, FORCE_CAST_MODERATE_OR_HEAVY_WITH_THUNDER
        -> R.drawable.storm

        FORCE_CAST_MODERATE_OR_HEAVY_SHOWER, FORCE_CAST_PATCHY_LIGHT_RAIN_WITH_THUNDER -> R.drawable.storm_showers_day

        else -> R.drawable.sky_blue
    }
    this.setBackgroundResource(imageId)
}

fun View.startCircularReveal(postX: Int?, postY: Int?) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int,
            oldRight: Int, oldBottom: Int
        ) {
            v.removeOnLayoutChangeListener(this)
            //TODO: Inject this from arguments
            val radius = hypot(right.toDouble(), bottom.toDouble()).toInt()
            ViewAnimationUtils.createCircularReveal(v, postX!!, postY!!, 0f, radius.toFloat())
                .apply {
                    interpolator = DecelerateInterpolator(2f)
                    duration = 1000
                    start()
                }
        }
    })
}

fun View.exitCircularReveal(exitX: Int, exitY: Int, block: () -> Unit) {
    val startRadius = hypot(this.width.toDouble(), this.height.toDouble())

    ViewAnimationUtils.createCircularReveal(this, exitX, exitY, startRadius.toFloat(), 0f).apply {
        duration = 350
        interpolator = DecelerateInterpolator(1f)
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                block()
                super.onAnimationEnd(animation)
            }
        })
        start()
    }
}

fun View.findLocationOfCenterOnTheScreen(): IntArray {
    val positions = intArrayOf(0, 0)
    getLocationInWindow(positions)
    positions[0] = positions[0] + width / 2
    positions[1] = positions[1] + height / 2
    return positions

}