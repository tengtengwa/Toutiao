package com.example.toutiao.ui.fragment.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.toutiao.logic.model.Location
import com.example.toutiao.logic.repository.WeatherRepository

class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()

    var lng = ""

    var lat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        WeatherRepository.refreshWeather(it.lng, it.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }

    fun getSavedPlace() = WeatherRepository.getSavedPlace()

    fun isPlaceSaved() = WeatherRepository.isPlaceSaved()
}