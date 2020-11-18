package com.example.weather.util

import com.example.weather.model.ForecastBase
import com.example.weather.services.ForecastWeatherService
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .build()

    private val forecastRetrofitConfig = Retrofit.Builder()
        .baseUrl(Constants.FORECAST_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun forecastService(api: String, city: String): Flowable<ForecastBase> {
        return forecastRetrofitConfig.create(ForecastWeatherService::class.java)
            .getForecastWeather(apiKey = api, city = city, days = 7)
    }
}