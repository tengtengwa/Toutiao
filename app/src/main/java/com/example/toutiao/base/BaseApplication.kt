package com.example.toutiao.base

import android.app.Application
import android.content.Context

class BaseApplication : Application() {

    companion object {
        lateinit var context: Context

        const val TOKEN = "93e9O2igK4D4IjWw"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }
}