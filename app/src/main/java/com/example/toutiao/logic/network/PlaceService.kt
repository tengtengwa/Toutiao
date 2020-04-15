package com.example.toutiao.logic.network

import com.example.toutiao.base.BaseApplication
import com.example.toutiao.logic.model.PlaceModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${BaseApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceModel>
}

