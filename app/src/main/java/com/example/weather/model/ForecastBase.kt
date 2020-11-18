package com.example.weather.model

data class ForecastBase(

    val location: Location,
    val current: Current,
    val forecast: Forecast
)