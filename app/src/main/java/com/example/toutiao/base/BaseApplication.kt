package com.example.toutiao.base

import android.app.Application
import android.content.Context
import com.tencent.smtt.sdk.QbSdk

class BaseApplication : Application() {

    companion object {
        lateinit var context: Context

        const val TOKEN = "93e9O2igK4D4IjWw"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        //初始化X5内核
        QbSdk.initX5Environment(this,object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {

            }

            override fun onViewInitFinished(p0: Boolean) {

            }
        })
    }
}