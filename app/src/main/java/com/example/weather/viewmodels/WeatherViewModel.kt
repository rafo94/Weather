package com.example.weather.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.model.ForecastBase
import com.example.weather.util.RetrofitConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val responseLiveData = MutableLiveData<ForecastBase>()
    val errorLiveData = MutableLiveData<Throwable>()

    fun sendRequest(cityName: String, apiKey: String) {
        compositeDisposable.add(
            RetrofitConfig.forecastService(apiKey, cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response ->
                    responseLiveData.postValue(response)
                }, {
                    errorLiveData.value = it
                })
        )
    }
}