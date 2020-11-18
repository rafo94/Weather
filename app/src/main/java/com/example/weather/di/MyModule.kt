package com.example.weather.di

import com.example.weather.adapter.ForceCastAdapter
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import java.util.*

val adapterModule = DI.Module("adapters") {

    bind<ForceCastAdapter>(tag = "forceCastAdapter") with singleton { ForceCastAdapter(ArrayList()) }
}