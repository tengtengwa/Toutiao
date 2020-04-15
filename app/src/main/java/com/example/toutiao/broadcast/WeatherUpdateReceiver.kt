package com.example.toutiao.broadcast

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.toutiao.service.WeatherUpdateService

class WeatherUpdateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent.setClass(context, WeatherUpdateService::class.java)
        context.startService(intent)

        val intent2 = Intent(context, WeatherUpdateReceiver::class.java)
        val pi = PendingIntent.getBroadcast(context, 1, intent2, 0)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, 1000 * 60 * 10, pi)      //10分钟自动唤醒并启动更新天气服务

    }
}
