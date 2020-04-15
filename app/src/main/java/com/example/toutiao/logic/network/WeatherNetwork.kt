package com.example.toutiao.logic.network

import com.example.toutiao.utils.await

object WeatherNetwork {

    private val placeService = ServiceCreator.create("https://api.caiyunapp.com/", PlaceService::class.java)

    private val weatherService = ServiceCreator.create("https://api.caiyunapp.com/", WeatherService::class.java)

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()

    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()

}