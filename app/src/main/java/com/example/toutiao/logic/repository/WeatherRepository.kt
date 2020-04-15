package com.example.toutiao.logic.repository

import androidx.lifecycle.liveData
import com.example.toutiao.logic.dao.PlaceDao
import com.example.toutiao.logic.model.Place
import com.example.toutiao.logic.model.WeatherModel
import com.example.toutiao.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object WeatherRepository {

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeModel = WeatherNetwork.searchPlaces(query)
        if (placeModel.status == "ok") {
            val places = placeModel.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("status abnormal"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async { WeatherNetwork.getRealtimeWeather(lng, lat) }
            val deferredDaily = async { WeatherNetwork.getDailyWeather(lng, lat) }
            val realtimeModel = deferredRealtime.await()
            val dailyModel = deferredDaily.await()
            if (realtimeModel.status == "ok" && dailyModel.status == "ok") {
                val weatherModel = WeatherModel(realtimeModel.result.realtime, dailyModel.result.daily)
                Result.success(weatherModel)
            } else {
                Result.failure(RuntimeException("request weather error"))
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) = liveData<Result<T>> {
        val result = try {
            block()
        } catch (e: Exception) {
            Result.failure<T>(e)
        }
        emit(result)
    }

}