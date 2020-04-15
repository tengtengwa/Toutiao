package com.example.toutiao.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.toutiao.utils.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope

class WeatherUpdateService : Service() {

    override fun onCreate() {
        super.onCreate()
        if (NetworkUtil.isNetworkConnected(this)) {
            //下面进行更新天气的逻辑
            updateWeather()
        }
        stopSelf()
    }

    private fun updateWeather() {


        val intent = Intent("com.example.toutiao.WEATHER_UPDATED")
        sendBroadcast(intent)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}
