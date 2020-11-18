package com.example.weather.di

import org.kodein.di.DI

val kodein = DI {
    import(adapterModule)
}