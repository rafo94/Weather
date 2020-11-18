package com.example.weather.model

data class Day(

    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val maxwind_mph: Double,
    val maxwind_kph: Double,
    val totalprecip_mm: Double,
    val totalprecip_in: Double,
    val avgvis_km: Double,
    val avgvis_miles: Int,
    val avghumidity: Int,
    val daily_will_it_rain: Int,
    val daily_chance_of_rain: String,
    val daily_will_it_snow: Int,
    val daily_chance_of_snow: String,
    val condition: Condition,
    val uv: Int
)