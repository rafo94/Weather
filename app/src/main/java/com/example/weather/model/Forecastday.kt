package com.example.weather.model

data class Forecastday(

	val date: String,
	val date_epoch: Int,
	val day: Day,
	val astro: Astro
)