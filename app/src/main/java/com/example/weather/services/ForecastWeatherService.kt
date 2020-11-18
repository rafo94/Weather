package com.example.weather.services

import com.example.weather.model.ForecastBase
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastWeatherService {

    @GET("v1/forecast.json")
    fun getForecastWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int
    ): Flowable<ForecastBase>
}