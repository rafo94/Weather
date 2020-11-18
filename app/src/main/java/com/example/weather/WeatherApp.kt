package com.example.weather

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}