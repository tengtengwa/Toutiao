package com.example.toutiao.logic.network

import com.example.toutiao.base.BaseApplication
import com.example.toutiao.logic.model.DailyModel
import com.example.toutiao.logic.model.RealtimeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${BaseApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeModel>

    @GET("v2.5/${BaseApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyModel>

}