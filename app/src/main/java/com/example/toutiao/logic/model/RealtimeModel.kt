package com.example.toutiao.logic.model

import com.google.gson.annotations.SerializedName

data class RealtimeModel(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(val skycon: String, val temperature: Float, @SerializedName("air_quality") val airCon: AriCon)

    data class AriCon(val aqi: AQI)

    data class AQI(val chn: Float)
}